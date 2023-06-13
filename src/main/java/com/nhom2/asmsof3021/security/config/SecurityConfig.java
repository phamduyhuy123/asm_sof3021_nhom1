package com.nhom2.asmsof3021.security.config;

import com.nhom2.asmsof3021.repository.UserRepository;
import com.nhom2.asmsof3021.security.auth.CustomAuthenticationSuccessHandler;
import com.nhom2.asmsof3021.security.auth.CustomLogoutSuccessHandler;
import com.nhom2.asmsof3021.security.enums.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;


@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
        private final RedirectStrategy redirectStrategy;
        private final UserRepository userRepository;
        private final CustomAuthenticationSuccessHandler userSuccessHandlerLogin;
        private final CustomLogoutSuccessHandler logoutSuccessHandler;
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
            http
                    .csrf()
                    .disable()
                    .securityMatcher("/admin/**")
                    .authorizeHttpRequests()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .and()
                    .formLogin(customizeLoginAdmin())
                    .logout(customizeLogoutAdmin());

            return http.build();
        }
       @Bean
       public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
           http
                   .csrf()
                   .disable()
                   .authorizeHttpRequests()
                   .requestMatchers("/user/**").hasRole(Role.USER.name())
                   .requestMatchers("/**")
                   .permitAll()
                   .anyRequest()
                   .authenticated()
                   .and()
                   .formLogin(customizeLoginUser())
                   .logout(customizeLogoutUser());

           return http.build();
       }

       public Customizer<FormLoginConfigurer<HttpSecurity>> customizeLoginUser(){
           return new Customizer<FormLoginConfigurer<HttpSecurity>>() {
               @Override
               public void customize(FormLoginConfigurer<HttpSecurity> formLoginConfigurer) {
                   formLoginConfigurer
                           .loginPage("/login")
                           .loginProcessingUrl("/authenticate")
                           .usernameParameter("email")
                           .passwordParameter("password")
                           .defaultSuccessUrl("/index")
                           .successHandler(userSuccessHandlerLogin)
                           .failureForwardUrl("/login")
                           .failureHandler(authenticationFailureHandlerUser())
                           .permitAll()
                   ;
               }
           };
       }
       public Customizer<FormLoginConfigurer<HttpSecurity>> customizeLoginAdmin(){
        return new Customizer<FormLoginConfigurer<HttpSecurity>>() {
            @Override
            public void customize(FormLoginConfigurer<HttpSecurity> formLoginConfigurer) {
                formLoginConfigurer
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/authenticate")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin/home")
                        .failureForwardUrl("/admin/login")
                        .failureHandler(authenticationFailureHandlerAdmin())
                        .permitAll()
                ;
            }
        };
    }
       public Customizer<LogoutConfigurer<HttpSecurity>> customizeLogoutUser(){
           return new Customizer<LogoutConfigurer<HttpSecurity>>() {
               @Override
               public void customize(LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer) {
                   httpSecurityLogoutConfigurer
                           .logoutUrl("/action_logout")
                           .deleteCookies("JSESSIOND")
                           .logoutSuccessUrl("/login?logout=true")
                           .logoutSuccessHandler(logoutSuccessHandler);

               }

           };
       }
       public Customizer<LogoutConfigurer<HttpSecurity>> customizeLogoutAdmin(){
        return new Customizer<LogoutConfigurer<HttpSecurity>>() {
            @Override
            public void customize(LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer) {
                httpSecurityLogoutConfigurer
                        .logoutUrl("/admin/action_logout")
                        .deleteCookies("JSESSIOND")
                        .logoutSuccessUrl("/admin/login");

            }

        };
    }

       public AuthenticationFailureHandler authenticationFailureHandlerUser(){
           AuthenticationFailureHandler authenticationFailureHandler=new AuthenticationFailureHandler() {
               @Override
               public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                    String email=String.valueOf(request.getParameter("email"));
                   redirectStrategy.sendRedirect(request, response, "/login?error=true&email="+email);

               }

           };
           return authenticationFailureHandler;
       }
       public AuthenticationFailureHandler authenticationFailureHandlerAdmin(){
        AuthenticationFailureHandler authenticationFailureHandler=new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

                response.sendRedirect("/admin/login?error=true");
            }

        };
        return authenticationFailureHandler;
    }



}