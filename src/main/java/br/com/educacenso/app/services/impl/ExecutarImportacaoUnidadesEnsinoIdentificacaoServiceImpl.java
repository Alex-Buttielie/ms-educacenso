package br.com.educacenso.app.services.impl;

import br.com.educacenso.app.services.ExecutarImportacaoUnidadesEnsinoIdentificacaoService;
import br.com.educacenso.app.domains.UnidadeEnsino;
import br.com.educacenso.app.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.architecture.GenericEducacensoImportacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Qualifier("unidadeEnsinoIndentificacao")
@Component
public class ExecutarImportacaoUnidadesEnsinoIdentificacaoServiceImpl
        extends GenericEducacensoImportacao
        implements ExecutarImportacaoUnidadesEnsinoIdentificacaoService {

    @Autowired
    @Qualifier("unidadeEnsinoRepository")
    private UnidadeEnsinoRepository unidadeEnsinoRepository;

    public ExecutarImportacaoUnidadesEnsinoIdentificacaoServiceImpl(UnidadeEnsinoRepository unidadeEnsinoRepository) {
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
    }

    @Override
    public Optional<UnidadeEnsino> importarLinhaArquivo(String[] conteudoLinha) {
        return Optional.of(salvarDadosUnidadeEnsino(atualizarDadosUnidadeEnsino(conteudoLinha)));
    }

    private UnidadeEnsino getDadosDaUnidadeEnsinoNaLinha(String[] conteudoLinha, Optional<UnidadeEnsino> unidadeEnsinoConsultadaOptional) {
        return new UnidadeEnsino()
                .builder()
                .build();
    }
    private UnidadeEnsino atualizarDadosUnidadeEnsino(String[] conteudoLinha) {
        return  Optional.ofNullable(unidadeEnsinoRepository.findById(stringToLong(conteudoLinha, 1)))
                .map(unidadeEnsinoConsultada -> atualizarDadosUnidadeEnsinoConsultada(unidadeEnsinoConsultada, conteudoLinha))
                .orElse(atualizarDadosUnidadeEnsinoNaoConsultada(conteudoLinha));

    }

    private UnidadeEnsino atualizarDadosUnidadeEnsinoNaoConsultada(String[] conteudoLinha) {
        return getDadosDaUnidadeEnsinoNaLinha(conteudoLinha, Optional.empty());
    }

    private UnidadeEnsino salvarDadosUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        return unidadeEnsinoRepository.save(unidadeEnsino);
    }

    private UnidadeEnsino atualizarDadosUnidadeEnsinoConsultada(Optional<UnidadeEnsino> unidadeEnsino,
                                                                String[] conteudoLinha) {
        return getDadosDaUnidadeEnsinoNaLinha(conteudoLinha, unidadeEnsino);
    }

}
