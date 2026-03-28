package com.security.security_jpa.services;


import com.security.security_jpa.entities.AppAccount;
import com.security.security_jpa.repositories.AppAccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class AccountDetailsService implements UserDetailsService{

    private final AppAccountRepository appAccountRepository;

    public AccountDetailsService(AppAccountRepository appAccountRepository) {
        this.appAccountRepository = appAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppAccount account = appAccountRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(
                account.getLogin(),
                account.getSecretKey(),
                account.isEnabled(),
                true, true, true,
                account.getAppRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList())
        );
    }
}