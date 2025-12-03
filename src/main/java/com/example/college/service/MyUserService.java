package com.example.college.service;

import com.example.college.model.Permission;
import com.example.college.model.UserModel;
import com.example.college.repository.PermissionRep;
import com.example.college.repository.UserRep;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {

    private final UserRep userRepository;
    private final PermissionRep permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email '" + email + "' not found");
        }

        return user;
    }

    public UserModel registerUser(UserModel request) {
        UserModel existed = userRepository.findByEmail(request.getEmail());
        if (existed != null) {
            return existed;
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        Permission userRole = permissionRepository.findByName("ROLE_USER");
        List<Permission> permissions = Collections.singletonList(userRole);
        request.setPermissions(permissions);

        return userRepository.save(request);
    }

    public void registr(UserModel model) {
        registerUser(model);
    }
}