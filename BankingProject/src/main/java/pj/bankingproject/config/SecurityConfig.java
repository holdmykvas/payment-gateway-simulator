package pj.bankingproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
        //Spring will block all POST requests from Swagger because it expects a hidden browser token if enabled
        http.csrf(csrf -> csrf.disable()); //cross-site request forgery

        //Spring prevents Clickjacking if enabled.
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http.authorizeHttpRequests(
            auth -> auth.requestMatchers
                ("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/h2-console/**")
                .permitAll().anyRequest().authenticated());

        http.httpBasic(org.springframework.security.config.Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
