package com.ravvy.gymtrack.config; // ajuste para o seu pacote

import java.util.Arrays;
import java.util.List;

import com.ravvy.gymtrack.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // habilita @PreAuthorize nos controllers/services (próxima etapa)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF protege sessões baseadas em cookie. Aqui não há sessão nem cookie:
                // o token vai no header Authorization, que o navegador não envia sozinho.
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})   // usa o bean corsConfigurationSource abaixo
                .sessionManagement(sessao ->
                        sessao.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Entrada no sistema: login e cadastros
                        .requestMatchers(HttpMethod.POST,
                                "/auth/login", "/aluno", "/professor", "/instituicao").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/auth/login", "/aluno", "/professor", "/instituicao", "/endereco").permitAll()
                        // Necessário para respostas de erro do Spring não virarem 401 falso
                        .requestMatchers("/error").permitAll()
                        // Swagger (desligar fora de desenvolvimento, veja application-prod)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Todo o resto exige token válido
                        .anyRequest().authenticated())
                .exceptionHandling(erros -> erros
                        .authenticationEntryPoint((request, response, ex) ->
                                escreverErro(response, HttpStatus.UNAUTHORIZED, "Não autenticado"))
                        .accessDeniedHandler((request, response, ex) ->
                                escreverErro(response, HttpStatus.FORBIDDEN, "Acesso negado")))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Usado pelo AuthController no login. O Spring monta o DaoAuthenticationProvider
     * sozinho a partir do UserDetailsService e do PasswordEncoder que existirem como beans.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuracao)
            throws Exception {
        return configuracao.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173}")
            String[] origensPermitidas) {

        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.setAllowedOrigins(Arrays.asList(origensPermitidas));
        configuracao.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuracao.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuracao.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource fonte = new UrlBasedCorsConfigurationSource();
        fonte.registerCorsConfiguration("/**", configuracao);
        return fonte;
    }

    private static void escreverErro(HttpServletResponse response, HttpStatus status, String mensagem)
            throws java.io.IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"status\":" + status.value() + ",\"mensagem\":\"" + mensagem + "\"}");
    }
}