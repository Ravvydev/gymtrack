
package com.ravvy.gymtrack.exportacao;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleSheetsExportacaoService {

    private final Sheets servicoGoogleSheets;

    public GoogleSheetsExportacaoService(Sheets servicoGoogleSheets) {
        this.servicoGoogleSheets = servicoGoogleSheets;
    }

    public Spreadsheet criarPlanilha() throws IOException {

        SpreadsheetProperties propriedadesPlanilha =
                new SpreadsheetProperties();

        propriedadesPlanilha.setTitle(
                "GymTrack - Exportação de Dados"
        );

        List<Sheet> abas = new ArrayList<>();

        abas.add(criarAba("Alunos"));
        abas.add(criarAba("Professores"));
        abas.add(criarAba("Instituicoes"));
        abas.add(criarAba("Avaliacoes"));
        abas.add(criarAba("Relatorios"));

        Spreadsheet planilha = new Spreadsheet();
        planilha.setProperties(propriedadesPlanilha);
        planilha.setSheets(abas);

        return servicoGoogleSheets
                .spreadsheets()
                .create(planilha)
                .execute();
    }

    private Sheet criarAba(String nomeAba) {

        SheetProperties propriedadesAba = new SheetProperties();
        propriedadesAba.setTitle(nomeAba);

        Sheet aba = new Sheet();
        aba.setProperties(propriedadesAba);

        return aba;
    }
}
