package com.ravvy.gymtrack.security.jwt;

import com.ravvy.gymtrack.shared.senhas.validation.SenhaValidator;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.usuario.entity.Usuario;
import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import com.ravvy.gymtrack.usuario.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Cria o usuário ADMIN na inicialização, uma única vez.
 * - Só cria se ainda NÃO existir nenhum ADMIN (pode subir a aplicação quantas vezes quiser).
 * - E-mail e senha vêm de variáveis de ambiente (ADMIN_EMAIL e ADMIN_SENHA), nunca do código.
 * - Se as variáveis não existirem, não cria ninguém e avisa no log.
 */
@Component
public class AdminInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminSenha;

    public AdminInitializer(UsuarioRepository usuarioRepository,
                            PasswordEncoder passwordEncoder,
                            @Value("${app.admin.email:}") String adminEmail,
                            @Value("${app.admin.senha:}") String adminSenha) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminSenha = adminSenha;
    }

    @Override
    public void run(ApplicationArguments args) {

        log.info("AdminInitializer executado. E-mail definido: {}, senha definida: {}",
                !adminEmail.isBlank(), !adminSenha.isBlank());

        if (usuarioRepository.existsByPapel(PapelUsuario.ADMIN)) {
            log.info("Já existe um ADMIN: nada a fazer.");
            return;
        }

        if (adminEmail.isBlank() || adminSenha.isBlank()) {
            log.warn("Nenhum ADMIN existe e ADMIN_EMAIL/ADMIN_SENHA não foram definidos: "
                    + "nenhum administrador foi criado.");
            return;
        }

        Email email = new Email(adminEmail); // normaliza e valida o formato

        if (usuarioRepository.findByEmailEndereco(email.getEndereco()).isPresent()) {
            log.warn("O e-mail configurado para o ADMIN já pertence a outro usuário: "
                    + "nenhum administrador foi criado.");
            return;
        }

        SenhaValidator.validar(adminSenha);

        Usuario admin = new Usuario(
                email,
                passwordEncoder.encode(adminSenha),
                PapelUsuario.ADMIN);

        usuarioRepository.save(admin);
        log.info("Usuário ADMIN criado com sucesso.");
    }
}