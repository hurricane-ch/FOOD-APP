package com.foodorderapp.services.impl;

import com.foodorderapp.exceptions.UserAlreadyExistAuthenticationException;
import com.foodorderapp.models.binding.auth.SignUpRequestBindingModel;
import com.foodorderapp.models.binding.user.UpdateRoles;
import com.foodorderapp.models.binding.user.UserRollView;
import com.foodorderapp.models.entity.Role;
import com.foodorderapp.models.entity.User;
import com.foodorderapp.models.service.UserServiceModel;
import com.foodorderapp.repositories.RoleRepository;
import com.foodorderapp.repositories.UserRepository;
import com.foodorderapp.services.interfaces.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(value = "transactionManager")
    public UserServiceModel registerNewUser(final SignUpRequestBindingModel signUpRequest) throws UserAlreadyExistAuthenticationException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email " + signUpRequest.getEmail() + " already exist");
        }
        User user = buildUser(signUpRequest);
        Date now = Calendar.getInstance().getTime();
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        user.setRoles(Set.of(roleRepository.findByName(Role.ROLE_USER)));
        user = userRepository.save(user);
//        userRepository.flush();
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    private User buildUser(final SignUpRequestBindingModel formDTO) {
        User user = new User();
        user.setDisplayName(formDTO.getDisplayName());
        user.setEmail(formDTO.getEmail());
        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
        final HashSet<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Role.ROLE_ADMIN));
        user.setRoles(roles);
        user.setEnabled(true);
        return user;
    }

    @Override
    public Optional<UserServiceModel> findUserById(Long id) {

        Optional<User> user = userRepository.findById(id);
        var userSM = this.modelMapper.map(user, UserServiceModel.class);
        return Optional.of(userSM);
    }

    @Override
    public void changeRole(UpdateRoles dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow();
        user.getRoles().clear();
        dto.getRolesName().forEach(role -> {
            user.getRoles().add(roleRepository.findByName(role));
        });

        userRepository.save(user);
    }

    @PostConstruct
    public void init() {
        if (this.roleRepository.count() == 0) {
            Role admin = new Role(Role.ROLE_ADMIN);
            Role user = new Role(Role.ROLE_USER);

            this.roleRepository.save(admin);
            this.roleRepository.save(user);
        }
    }

    @Override
    public UserServiceModel updateExistingUser(Long id,String name, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistAuthenticationException("User with email " + email + " already exist");
        }
        User user = this.userRepository.findById(id).orElseThrow();

        if (StringUtils.hasText(name)) {
            user.setDisplayName(name);
        }

        if (StringUtils.hasText(email)) {
            user.setEmail(email);
        }

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<UserRollView> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserRollView> userRollViews = new ArrayList<>();

        users.forEach(user -> {
           UserRollView userRollView = new UserRollView();
              userRollView.setId(user.getId().toString());
                userRollView.setDisplayName(user.getDisplayName());
                userRollView.setEmail(user.getEmail());
                userRollView.setRolesName(user.getRoles().stream().map(Role::getName).toList());
                userRollViews.add(userRollView);
        });


        return userRollViews;
    }
}
