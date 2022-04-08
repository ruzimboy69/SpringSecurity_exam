package com.example.springsecurity_exam.component;

import com.example.springsecurity_exam.entity.Role;
import com.example.springsecurity_exam.entity.User;
import com.example.springsecurity_exam.entity.enums.PermissionEnum;
import com.example.springsecurity_exam.entity.enums.RoleEnum;
import com.example.springsecurity_exam.repository.ProductRepository;
import com.example.springsecurity_exam.repository.RoleRepository;
import com.example.springsecurity_exam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DataLoader  implements CommandLineRunner {
    final PasswordEncoder passwordEncoder;

    @org.springframework.beans.factory.annotation.Value("${spring.sql.init.mode}")
    private String mode;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;


    final UserRepository userRepository;
    final ProductRepository productRepository;
    final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always") && ddl.equals("create")) {
            Role firstAdmin = new Role();
            firstAdmin.setName(RoleEnum.ADMIN);
            firstAdmin.setPermissionEnumSet(Arrays.stream(PermissionEnum.values()).collect(Collectors.toSet()));


            Role secondAdmin = new Role();
            secondAdmin.setName(RoleEnum.ADMIN);
            secondAdmin.setPermissionEnumSet(new HashSet<>(Arrays.asList(
                    PermissionEnum.READ_ALL_PRODUCT,
                    PermissionEnum.READ_PRODUCT,
                    PermissionEnum.ADD_PRODUCT
            )));

            Role user_role = new Role();
            user_role.setName(RoleEnum.USER);
            user_role.setPermissionEnumSet(new HashSet<>(Arrays.asList(
                    PermissionEnum.READ_PRODUCT,
                    PermissionEnum.READ_ALL_PRODUCT
            )));
            roleRepository.save(firstAdmin);
            roleRepository.save(secondAdmin);
            roleRepository.save(user_role);


            Set<Role> roles = new HashSet<>();
            roles.add(firstAdmin);
            roles.add(secondAdmin);
            roles.add(user_role);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
           user.setRoles(new HashSet<>(List.of(user_role)));
            userRepository.save(user);

            User admin1 = new User();
            admin1.setUsername("admin1");
            admin1.setPassword(passwordEncoder.encode("admin1"));
            admin1.setRoles(roles);
            userRepository.save(admin1);

            User admin2 = new User();
            admin1.setUsername("admin2");
            admin1.setPassword(passwordEncoder.encode("admin2"));
            admin1.setRoles(new HashSet<>(List.of(secondAdmin)));
            userRepository.save(admin2);
        }


    }
}
