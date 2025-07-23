package com.noBroker.nobroker_application_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error","/landingPage","loginPage", "/send-otp", "/verify","verify-otp").permitAll()
                        .requestMatchers("/viewProperty/**", "/view-full-property", "edit/**").hasAnyRole("OIDC_USER","USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/saveUser", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(authorities -> {
                                    Set<GrantedAuthority> mappedAuthorities = new HashSet<>(authorities);

                                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                                    return mappedAuthorities;
                                })
                        )

                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/landingPage")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}