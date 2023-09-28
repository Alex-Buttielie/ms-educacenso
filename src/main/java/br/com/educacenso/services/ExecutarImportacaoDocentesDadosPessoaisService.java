package br.com.educacenso.services;

import br.com.educacenso.app.docente.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.docente.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.pessoa.constraints.MaiorNivelEscolaridadeConcluido;
import br.com.educacenso.app.pessoa.constraints.Nacionalidade;
import br.com.educacenso.app.pessoa.domains.Pessoa;

import java.util.Optional;

public interface ExecutarImportacaoDocentesDadosPessoaisService extends ExecutarImportacaoService {
    Pessoa atualizarDadosPessoa(String[] linhaNovosDadosDocente);
    Optional<Pessoa> rastrearPessoaCacteristicasIndiv(String[] conteudoLinha);
    Optional<Pessoa> rastrearPessoaCpf(String[] conteudoLinha);
    Optional<Pessoa> rastrearPessoaNome(String[] dadosPessoaStr);
    Pessoa atualizarDadosPessoaConsultada(Optional<Pessoa> pessoa, String[] conteudoLinha);
    Pessoa atualizarDadosPessoaNaoConsultada(String[] conteudo);
    LocalizacaoZonaResidencia getLocalizacaoZonaResidencia(String conteudo);
    Nacionalidade getNacionalidade(String conteudo);
    MaiorNivelEscolaridadeConcluido getMaiorNivelEscolaridadeConcluida(String conteudo);
    TipoEnsinoMedioCursado getTipoEnsinoMedioCursado(String conteudo);
}
