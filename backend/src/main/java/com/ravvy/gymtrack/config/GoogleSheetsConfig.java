package com.ravvy.gymtrack.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleSheetsConfig {

    @Bean
    public Sheets sheetsClient()
            throws IOException, GeneralSecurityException {
        // Implemente a configuração aqui

        GoogleCredentials credenciais =
                GoogleCredentials.getApplicationDefault();

        credenciais = credenciais.createScoped(
                Collections.singleton(SheetsScopes.SPREADSHEETS)
        );

        var transporte = GoogleNetHttpTransport.newTrustedTransport();
        var autenticacao = new HttpCredentialsAdapter(credenciais);

        return new Sheets.Builder(
                transporte,
                GsonFactory.getDefaultInstance(),
                autenticacao
        )
                .setApplicationName("GymTrack")
                .build();

    }

}
