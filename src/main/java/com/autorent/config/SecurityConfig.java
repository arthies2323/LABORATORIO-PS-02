package com.autorent.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import com.autorent.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JpaUserDetailsService userDetailsService;

    public SecurityConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        // Create MvcRequestMatcher.Builder for Spring MVC patterns
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        
        http
                .authorizeHttpRequests(auth -> auth
                // Use MvcRequestMatcher for Spring MVC endpoints
                .requestMatchers(
                    mvcMatcherBuilder.pattern("/css/**"),
                    mvcMatcherBuilder.pattern("/js/**"),
                    mvcMatcherBuilder.pattern("/register"),
                    mvcMatcherBuilder.pattern("/"),
                    mvcMatcherBuilder.pattern("/login")
                ).permitAll()
                // Use AntPathRequestMatcher for H2 console (non-Spring MVC servlet)
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(mvcMatcherBuilder.pattern("/agente/**")).hasRole("AGENTE")
                .requestMatchers(
                    mvcMatcherBuilder.pattern("/clientes/**"),
                    mvcMatcherBuilder.pattern("/veiculos/**"),
                    mvcMatcherBuilder.pattern("/pedidos/**")
                ).hasRole("CLIENTE")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        http.userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
