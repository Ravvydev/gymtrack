package com.ravvy.gymtrack.security.jwt; // ajuste para o seu pacote

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Roda uma vez por requisição. Se vier um "Authorization: Bearer <token>" válido,
 * recarrega o usuário do banco e o coloca no SecurityContext. Se o token for
 * inválido, expirado ou o usuário não existir mais, NÃO barra a requisição aqui:
 * apenas deixa sem autenticação, e as regras do SecurityFilterChain respondem 401.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIXO = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null
                && header.startsWith(PREFIXO)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            String token = header.substring(PREFIXO.length()).trim();

            jwtService.extrairEmailSeValido(token).ifPresent(email -> {
                try {
                    UserDetails usuario = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken autenticacao =
                            new UsernamePasswordAuthenticationToken(
                                    usuario, null, usuario.getAuthorities());
                    autenticacao.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                } catch (UsernameNotFoundException e) {
                    // Usuário apagado depois que o token foi emitido: segue sem autenticação.
                }
            });
        }

        filterChain.doFilter(request, response);
    }
}