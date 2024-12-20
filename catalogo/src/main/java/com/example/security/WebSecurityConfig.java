package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Value("${jwt.key.public}")
	private String SECRET;
	@Autowired
	private UnauthorizedHandler unauthorizedHandler;
	
	@Autowired
	AuthenticationEventPublisher authenticationEventPublisher;
	@Autowired(required = false)
	AuditEventRepository auditEventRepository;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(configurer -> {
                	configurer.authenticationEntryPoint(unauthorizedHandler);
                	configurer.accessDeniedHandler(unauthorizedHandler);
                })
//                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/error").permitAll()
//                    .requestMatchers("/actores/v1/**").hasRole("ADMINISTRADORES")
                    .anyRequest().permitAll()
                 )
                .addFilterBefore(new JWTAuthorizationFilter(SECRET, authenticationEventPublisher, auditEventRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
