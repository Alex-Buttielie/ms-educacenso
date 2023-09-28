package br.com.educacenso.services;

import java.text.ParseException;

public interface ExecutarImportacaoService {
    void importarLinhaArquivo(String[] conteudoLinha) throws ParseException;
}
