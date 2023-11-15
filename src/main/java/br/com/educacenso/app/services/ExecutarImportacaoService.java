package br.com.educacenso.app.services;

import java.text.ParseException;
import java.util.Optional;

public interface ExecutarImportacaoService {
    Optional<?> importarLinhaArquivo(String[] conteudoLinha) throws ParseException;
}
