package com.foodorderapp.config;

import com.foodorderapp.models.entity.Product;
import com.foodorderapp.models.entity.Role;
import com.foodorderapp.models.entity.User;
import com.foodorderapp.models.service.ImageServiceModel;
import com.foodorderapp.repositories.ProductRepository;
import com.foodorderapp.repositories.RoleRepository;
import com.foodorderapp.repositories.UserRepository;
import com.foodorderapp.services.impl.ImageServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageServiceImpl imageService;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // Create initial roles
        Role userRole = createRoleIfNotFound(Role.ROLE_USER);
        Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
        createAdminIfNotFound("admin", Set.of(adminRole));
        createUserIfNotFound("boev@abv.bg", Set.of(userRole));
        if (productRepository.count() == 0) {
            createProducts();
        }
        alreadySetup = true;
    }


    @Transactional
    public User createAdminIfNotFound(final String email, Set<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setDisplayName("Admin");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(roles);
            user.setEnabled(true);
            Date now = Calendar.getInstance().getTime();
            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user = userRepository.save(user);
        }
        return user;
    }

    @Transactional
    public User createUserIfNotFound(final String email, Set<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setDisplayName("Nikolay Boev");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(roles);
            user.setEnabled(true);
            Date now = Calendar.getInstance().getTime();
            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user = userRepository.save(user);
        }
        return user;
    }

    @Transactional
    public Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new Role(name));
        }
        return role;
    }

    @Transactional
    public void createProducts() {
       productRepository.save(new Product("Мляко с ориз", "С канела", "Десерт", 150, 5.0, true,null));
       productRepository.save(new Product("Овчарска салата", "С много колбас !", "Салата", 350, 5.0, true,null));
       productRepository.save(new Product("Таратор", "Без копър !", "Супа", 375, 3.0, true,null));
       productRepository.save(new Product("Кока кола", "От едно време !", "Напитка", 250, 2.0, true,null));
       productRepository.save(new Product("Мусака", "По домашна рецепта !", "Осново ястие", 450, 8.0, true,null));
    }
}