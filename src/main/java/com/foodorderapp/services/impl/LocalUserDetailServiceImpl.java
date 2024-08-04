package com.foodorderapp.services.impl;

import com.foodorderapp.exceptions.ResourceNotFoundException;
import com.foodorderapp.models.binding.user.LocalUserBindingModel;
import com.foodorderapp.models.entity.Role;
import com.foodorderapp.models.entity.User;
import com.foodorderapp.models.service.UserServiceModel;
import com.foodorderapp.repositories.RoleRepository;
import com.foodorderapp.repositories.UserRepository;
import com.foodorderapp.services.interfaces.UserService;
import com.foodorderapp.util.GeneralUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LocalUserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public LocalUserBindingModel loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        UserServiceModel user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return createLocalUser(this.modelMapper.map(user, User.class));
    }

    @Transactional
    public LocalUserBindingModel loadUserById(Long id) {
        var user = userService
                .findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return createLocalUser(this.modelMapper.map(user, User.class));
    }

    private LocalUserBindingModel createLocalUser(User user) {
       var u = this.userRepository.findByEmail(user.getEmail());

        return new LocalUserBindingModel(
                u.getEmail(),
                u.getPassword(),
                u.isEnabled(),
                true,
                true,
                true,
                GeneralUtils.buildSimpleGrantedAuthorities(u.getRoles()), u);
    }
}