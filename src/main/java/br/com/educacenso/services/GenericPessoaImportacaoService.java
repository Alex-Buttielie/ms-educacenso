package br.com.educacenso.services;

import br.com.educacenso.app.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.domains.Pessoa;

import java.util.Optional;

public interface GenericPessoaImportacaoService {
    Pessoa atualizarDadosPessoa(String [] conteudoLinha);
    Pessoa atualizarDadosPessoaConsultada(Optional<Pessoa> pessoaConsultada, String[] conteudoLinha);
    Pessoa atualizarDadosPessoaNaoConsultada(String[] conteudoLinha);
    LocalizacaoZonaResidencia getLocalizacaoZonaResidencia(String conteudo);
}


