package com.security.security_jpa.config;


import com.security.security_jpa.services.AccountDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityDatabaseConfig {

    private final AccountDetailsService accountDetailsService;

    public SecurityDatabaseConfig(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(accountDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain appFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth-signin", "/signup").permitAll()
                        .requestMatchers("/sys-admin/**").hasRole("SUPERVISOR")
                        .requestMatchers("/member/**").hasAnyRole("MEMBER", "SUPERVISOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth-signin")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/auth-signin?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/sign-out")
                        .logoutSuccessUrl("/auth-signin?logout=true")
                        .permitAll()
                );
        return http.build();
    }
}