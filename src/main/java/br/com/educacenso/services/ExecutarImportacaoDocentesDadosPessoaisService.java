package br.com.educacenso.services;

import br.com.educacenso.app.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.constraints.MaiorNivelEscolaridadeConcluido;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.constraints.TipoFiliacao;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.TipoDeficienciaEspectroAltasHabilidades;

import java.util.Optional;

public interface ExecutarImportacaoDocentesDadosPessoaisService extends ExecutarImportacaoService {
    Optional<Pessoa> rastrearPessoaCacteristicasIndiv(String[] conteudoLinha);
    Optional<Pessoa> rastrearPessoaCpf(String[] conteudoLinha);
    Optional<Pessoa> rastrearPessoaNome(String[] dadosPessoaStr);
    LocalizacaoDiferenciadaResidencia getLocalizacaoDiferenciadaResidencia(String conteudo);
    Nacionalidade getNacionalidade(String conteudo);
    MaiorNivelEscolaridadeConcluido getMaiorNivelEscolaridadeConcluida(String conteudo);
    TipoEnsinoMedioCursado getTipoEnsinoMedioCursado(String conteudo);
    TipoFiliacao getTipoFiliacao(String conteudo);
    TipoDeficienciaEspectroAltasHabilidades getTipoDeficienciaEspectroAltasHabilidades(String[] conteudoLinha, Optional<Pessoa> pessoaConsultadaOptional);
    Pessoa getDadosPessoaNaLinha(String[] conteudoLinha, Optional<Pessoa> pessoaConsultadaOptional);

}
