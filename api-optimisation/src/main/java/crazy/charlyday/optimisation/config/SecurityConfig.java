package crazy.charlyday.optimisation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Autoriser tout
            .csrf(csrf -> csrf.disable()) // Désactiver la protection CSRF (attention en prod)
            .formLogin(form -> form.disable()) // Désactiver la page de login
            .httpBasic(basic -> basic.disable()); // Désactiver l’authentification basique

        return http.build();
    }
}
