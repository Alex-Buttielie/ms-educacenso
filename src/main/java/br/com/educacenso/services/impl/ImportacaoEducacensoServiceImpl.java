package br.com.educacenso.services.impl;

import br.com.educacenso.contraints.TipoImportacaoEducacenso;
import br.com.educacenso.contraints.TipoRegistro;
import br.com.educacenso.utils.UtilFile;
import br.com.educacenso.services.ExecutarImportacaoService;
import br.com.educacenso.services.ImportacaoEducacensoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

@Service
public class ImportacaoEducacensoServiceImpl implements ImportacaoEducacensoService {

    private String[] conteudoLinha;

    @Override
    public void importarEducacenso(MultipartFile arquivo) throws IOException {
        BufferedReader reader = UtilFile.converterFileToBufferedReader(arquivo);
        String linha;
        try {
            while ((linha = reader.readLine()) != null) {
                conteudoLinha = getConteudoLinha(linha);
                if (conteudoLinha[0].equals(TipoRegistro.REGISTRO_CADASTRO_DOCENTE_IDENTIFICACAO.getDescricao()) ||
                        conteudoLinha[0].equals(TipoRegistro.REGISTRO_CADASTRO_ESCOLA_CARACTERIZACAO_INFRAESTRUTURA.getDescricao())) {
                    getTipoImportacaoEducacenso().importarLinhaArquivo(conteudoLinha);
                }
                limparConteudoLinha();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        reader.close();

    }

    private void limparConteudoLinha() {
        this.conteudoLinha  = null;
    }

    private ExecutarImportacaoService getTipoImportacaoEducacenso() {
        return TipoImportacaoEducacenso
                .values()[Integer.parseInt(TipoRegistro.getValorPorDescricao(conteudoLinha[0]))]
                .getTipoImportacao();
    }

    private String[] getConteudoLinha(String linha) {
        return linha.split("\\|");
    }

}
