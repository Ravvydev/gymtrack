package com.ravvy.gymtrack.auth.controller; // ajuste para o seu pacote

import com.ravvy.gymtrack.security.autenticacao.UsuarioAutenticado;
import com.ravvy.gymtrack.security.dto.LoginRequest;
import com.ravvy.gymtrack.security.dto.LoginResponse;
import com.ravvy.gymtrack.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
            Authentication autenticacao = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.senha()));

            UsuarioAutenticado usuario = (UsuarioAutenticado) autenticacao.getPrincipal();
            String token = jwtService.gerarToken(usuario);

            return ResponseEntity.ok(new LoginResponse(
                    token, "Bearer", jwtService.getExpiracaoSegundos(), usuario.getPapel()));

        } catch (AuthenticationException e) {
            // Resposta idêntica para e-mail inexistente e senha errada.
            // Tratado aqui (e não por exceção) para um handler global com
            // @ExceptionHandler(Exception.class) não transformar isso em 500.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}