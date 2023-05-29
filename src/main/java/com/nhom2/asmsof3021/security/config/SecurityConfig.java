package com.nhom2.asmsof3021.security.config;

import com.nhom2.asmsof3021.security.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.nhom2.asmsof3021.security.enums.Permission.*;
import static com.nhom2.asmsof3021.security.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig  {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()

                .requestMatchers("/admin","/admin/**").hasRole(ADMIN.name())
                .requestMatchers("/user/**","/user").hasRole(USER.name())
                .anyRequest()
                .permitAll()
                .and()
                .formLogin(customizeFormlogin())
                .logout(customizeLogout())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)


        ;

        return http.build();
    }
    public Customizer<FormLoginConfigurer<HttpSecurity>> customizeFormlogin(){

        return new Customizer<FormLoginConfigurer<HttpSecurity>>() {
            @Override
            public void customize(FormLoginConfigurer<HttpSecurity> formLoginConfigurer) {
                formLoginConfigurer.loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .failureUrl("/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(savedRequestAwareAuthenticationSuccessHandler());
            }
        };
    }
    public Customizer<LogoutConfigurer<HttpSecurity>> customizeLogout(){
        return new Customizer<LogoutConfigurer<HttpSecurity>>() {
            @Override
            public void customize(LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer) {
                httpSecurityLogoutConfigurer
                        .logoutUrl("/action_logout")
                        .deleteCookies("JSESSIOND")
                        .logoutSuccessUrl("/login")
                        .addLogoutHandler(logoutHandler);
            }

        };
    }
    public AuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler(){
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("targetUrl");
        return successHandler;
    }
}