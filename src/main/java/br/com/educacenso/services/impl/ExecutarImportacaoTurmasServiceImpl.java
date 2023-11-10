package br.com.educacenso.services.impl;

import br.com.educacenso.app.domains.Turma;
import br.com.educacenso.repositories.TurmaRepository;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.architecture.GenericEducation;
import br.com.educacenso.services.ExecutarImportacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Qualifier("turmas")
@Component
public class ExecutarImportacaoTurmasServiceImpl
        extends GenericEducation
        implements ExecutarImportacaoService {

    @Autowired
    @Qualifier("turmaRepository")
    private TurmaRepository turmaRepository;

    @Autowired
    @Qualifier("unidadeEnsinoRepository")
    private UnidadeEnsinoRepository unidadeEnsinoRepository;

    public ExecutarImportacaoTurmasServiceImpl(TurmaRepository turmaRepository,
                                               UnidadeEnsinoRepository unidadeEnsinoRepository) {
        this.turmaRepository = turmaRepository;
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
    }

    @Override
    public void importarLinhaArquivo(String[] conteudoLinha) {
        salvarDadosTurma(atualizarDadosTurma(conteudoLinha));
    }

    private Optional<Turma> atualizarDadosTurma(String[] conteudoLinha) {
        return Optional.ofNullable(turmaRepository.findTurmaByCodigoInep(stringToLong(conteudoLinha,4 )))
                .map(turmaConsultada -> atualizarDadosTurmaConsultada(turmaConsultada, conteudoLinha));

    }

    private Turma getDadosDaTurmaNaLinha(String[] conteudoLinha) {
        return new Turma()
                .builder()
                .build();

    }

    private Turma atualizarDadosTurmaNaoConsultada(String[] conteudoLinha) {
        return getDadosDaTurmaNaLinha(conteudoLinha);
    }

    private Turma salvarDadosTurma(Optional<Turma> turma) {
        return turmaRepository.saveAndFlush(turma.get());
    }

    private Turma atualizarDadosTurmaConsultada(Turma turmaConsultada, String[] conteudoLinha) {
        Turma turmAtualizada = getDadosDaTurmaNaLinha(conteudoLinha);
        turmAtualizada.setId(turmaConsultada.getId());
        return turmAtualizada;
    }

}
