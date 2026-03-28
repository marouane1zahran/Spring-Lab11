package com.security.security_jpa.config;

import com.security.security_jpa.entities.AppAccount;
import com.security.security_jpa.entities.AppRole;
import com.security.security_jpa.repositories.AppAccountRepository;
import com.security.security_jpa.repositories.AppRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(AppRoleRepository roleRepo, AppAccountRepository accountRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            AppRole supervisorRole = roleRepo.save(new AppRole(null, "ROLE_SUPERVISOR"));
            AppRole memberRole = roleRepo.save(new AppRole(null, "ROLE_MEMBER"));

            AppAccount supervisor = new AppAccount(null, "supervisor", encoder.encode("pass999"), true, List.of(supervisorRole, memberRole));
            AppAccount member = new AppAccount(null, "member", encoder.encode("pass888"), true, List.of(memberRole));

            accountRepo.saveAll(List.of(supervisor, member));
        };
    }
}