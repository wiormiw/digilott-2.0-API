package com.wiormiw.digilott_20.v1.infrastructure.config;

import com.wiormiw.digilott_20.v1.domain.models.Role;
import com.wiormiw.digilott_20.v1.infrastructure.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) { // Only insert if roles are missing
            Role userRole = new Role();
            userRole.setName(Role.RoleType.USER);
            roleRepository.save(userRole);

            Role adminRole = new Role();
            adminRole.setName(Role.RoleType.ADMIN);
            roleRepository.save(adminRole);

            System.out.println("Default roles USER and ADMIN created!");
        }
    }
}

