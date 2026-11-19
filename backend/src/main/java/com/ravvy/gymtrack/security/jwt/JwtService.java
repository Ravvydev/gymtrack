package com.ravvy.gymtrack.security.jwt;

import com.ravvy.gymtrack.security.autenticacao.UsuarioAutenticado;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

/**
 * Gera e valida os JWTs. O token carrega só o e-mail (subject), a data de emissão
 * e a expiração. O papel NÃO vai no token: a fonte da verdade é o banco, que o
 * filtro consulta a cada requisição.
 */
@Service
public class JwtService {

    private static final String EMISSOR = "gymtrack";

    private final SecretKey chave;
    private final long expiracaoMinutos;

    public JwtService(
            // Sem valor padrão de propósito: se a variável não existir, a aplicação não sobe.
            @Value("${app.security.jwt.secret}") String segredoBase64,
            @Value("${app.security.jwt.expiration-minutes}") long expiracaoMinutos) {

        byte[] bytes = Decoders.BASE64.decode(segredoBase64);
        if (bytes.length < 32) {
            throw new IllegalStateException(
                    "JWT_SECRET precisa ter pelo menos 256 bits (32 bytes) em Base64");
        }
        this.chave = Keys.hmacShaKeyFor(bytes);
        this.expiracaoMinutos = expiracaoMinutos;
    }

    public String gerarToken(UsuarioAutenticado usuario) {
        Instant agora = Instant.now();

        return Jwts.builder()
                .issuer(EMISSOR)
                .subject(usuario.getUsername())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plus(expiracaoMinutos, ChronoUnit.MINUTES)))
                .signWith(chave)
                .compact();
    }

    /**
     * Devolve o e-mail (subject) se o token for válido: assinatura correta,
     * emissor correto e não expirado. Qualquer problema vira Optional.empty().
     */
    public Optional<String> extrairEmailSeValido(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(chave)
                    .requireIssuer(EMISSOR)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Optional.ofNullable(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public long getExpiracaoSegundos() {
        return expiracaoMinutos * 60;
    }
}