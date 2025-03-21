package br.com.educacenso.app.services.impl;

import br.com.educacenso.app.services.ImportacaoEducacensoService;
import br.com.educacenso.app.constraints.TipoImportacaoEducacenso;
import br.com.educacenso.app.constraints.TipoRegistro;
import br.com.educacenso.app.services.ExecutarImportacaoService;
import br.com.educacenso.utils.UtilFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@Service
public class ImportacaoEducacensoServiceImpl implements ImportacaoEducacensoService {

    private String[] conteudoLinha;

    @Override
    public void importarEducacenso(MultipartFile arquivo) throws IOException {
        var reader = UtilFile.converterFileToBufferedReader(arquivo);
        String linha;
        try {
            while ((linha = reader.readLine()) != null) {
                conteudoLinha = getConteudoLinha(linha);
                if (conteudoLinha[0].equals(TipoRegistro.REGISTRO_CADASTRO_DOCENTE_IDENTIFICACAO.getDescricao()) ||
                        conteudoLinha[0].equals(TipoRegistro.REGISTRO_CADASTRO_TURMA.getDescricao()) ||
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
