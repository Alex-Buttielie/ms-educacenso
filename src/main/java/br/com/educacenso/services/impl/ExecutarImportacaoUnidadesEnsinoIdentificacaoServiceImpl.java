package br.com.educacenso.services.impl;

import br.com.educacenso.services.ExecutarImportacaoUnidadesEnsinoIdentificacaoService;
import br.com.educacenso.app.domains.UnidadeEnsino;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
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
    public void importarLinhaArquivo(String[] conteudoLinha) {
        salvarDadosUnidadeEnsino(atualizarDadosUnidadeEnsino(conteudoLinha));
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
        UnidadeEnsino unidadeEnsinoAtualizada = getDadosDaUnidadeEnsinoNaLinha(conteudoLinha, unidadeEnsino);
        return unidadeEnsinoAtualizada;
    }

}
