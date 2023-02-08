package ru.gb.market.authservice.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import ru.gb.market.authservice.services.UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//@Bean
//public WebSecurityCustomizer webSecurityCustomizer() {
//    return web -> web.ignoring().requestMatchers("/api/v1/auth/");
//}
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/core/api/v1/products/**").permitAll()
////                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("SUPERADMIN")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("SUPERADMIN")
////                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole("ADMIN", "SUPERADMIN")
////                .requestMatchers( HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
////                .requestMatchers( HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")
////                .requestMatchers( HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")
//                .anyRequest().permitAll()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .headers().frameOptions().disable()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .and()
//                .authenticationProvider(authenticationProvider());
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userService;
//
//    }
//
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
