package br.com.educacenso.app.services.impl;

import br.com.educacenso.app.constraints.TipoMediacao;
import br.com.educacenso.app.domains.DiasSemanaTurma;
import br.com.educacenso.app.domains.HorarioFuncionamentoTurma;
import br.com.educacenso.app.domains.Turma;
import br.com.educacenso.app.repositories.HorarioFuncionamentoTurmaRepository;
import br.com.educacenso.app.repositories.TurmaRepository;
import br.com.educacenso.app.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.app.services.ExecutarImportacaoService;
import br.com.educacenso.architecture.GenericEducacensoImportacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ExecutarImportacaoTurmasServiceImpl extends GenericEducacensoImportacao implements ExecutarImportacaoService {

    private TurmaRepository turmaRepository;
    private UnidadeEnsinoRepository unidadeEnsinoRepository;
    private HorarioFuncionamentoTurmaRepository horarioFuncionamentoTurmaRepository;

    @Autowired
    public ExecutarImportacaoTurmasServiceImpl(TurmaRepository turmaRepository,
                                               UnidadeEnsinoRepository unidadeEnsinoRepository) {
        this.turmaRepository = turmaRepository;
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
    }

    @Override
    public Optional<Turma> importarLinhaArquivo(String[] conteudoLinha) {
        return Optional.ofNullable(atualizarDadosTurma(conteudoLinha))
                .map(Optional::of)
                .map(this::salvarDadosTurma)
                .stream()
                .findAny();
    }

    private Turma atualizarDadosTurma(String[] conteudoLinha) {
        return Optional.ofNullable(turmaRepository.findTurmaByCodigoTurma(valorString(conteudoLinha,2)))
                .map(turmaConsultada -> atualizarDadosTurmaConsultada(Optional.of(turmaConsultada), conteudoLinha))
                .orElse(atualizarDadosTurmaNaoConsultada(conteudoLinha));

    }

    private Turma getDadosDaTurmaNaLinha(String[] conteudoLinha, Optional<Turma> turmaConsultada) {
        return new Turma()
                .builder()
                .id(turmaConsultada.map(Turma::getId).orElse(null))
                .unidadeEnsino(unidadeEnsinoRepository.findById(stringToLong(conteudoLinha, 1)).orElse(null))
                .codigoTurma(valorString(conteudoLinha, 2))
                .codigoInep(stringToLong(conteudoLinha, 3))
                .nome(valorString(conteudoLinha, 4))
                .tipoMediacao(buscarMediacao(valorString(conteudoLinha, 5)))
                .horarioFuncionamento(buscarHorarioFuncionamentoTurma(conteudoLinha, turmaConsultada))
                .build();

    }

    protected HorarioFuncionamentoTurma buscarHorarioFuncionamentoTurma(String[] conteudoLinha, Optional<Turma> turma) {
        try {
            var horario = turma
                    .map(Turma::getHorarioFuncionamento)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  HorarioFuncionamentoTurma
                    .builder()
                    .id(horario.map(HorarioFuncionamentoTurma::getId).orElse(null))
                    .horaInicial(valorString(conteudoLinha, 6))
                    .minutoInicial(valorString(conteudoLinha, 7))
                    .horaFinal(valorString(conteudoLinha, 8))
                    .minutoFinal(valorString(conteudoLinha, 9))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    protected DiasSemanaTurma buscarDiasSemanaDaTurma(String[] conteudoLinha, Optional<Turma> turma) {
        try {
            var dias = turma
                    .map(Turma::getDiasSemana)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  DiasSemanaTurma
                    .builder()
                    .id(dias.map(DiasSemanaTurma::getId).orElse(null))
                    .domingo(stringToBoolean(conteudoLinha, 10))
                    .segunda(stringToBoolean(conteudoLinha, 11))
                    .terca(stringToBoolean(conteudoLinha, 12))
                    .quarta(stringToBoolean(conteudoLinha, 13))
                    .quinta(stringToBoolean(conteudoLinha, 14))
                    .sexta(stringToBoolean(conteudoLinha, 15))
                    .sabado(stringToBoolean(conteudoLinha, 16))
                    .build();

        }catch (Exception e) {
            return null;
        }

    }

    protected TipoMediacao buscarMediacao(String conteudo) {
        return (TipoMediacao) buscaRegistroConteudoLido(TipoMediacao.getOrdinalPeloCodigo(conteudo), TipoMediacao.values());
    }

    private Turma atualizarDadosTurmaNaoConsultada(String[] conteudoLinha) {
        return getDadosDaTurmaNaLinha(conteudoLinha, Optional.empty());
    }

    private Turma salvarDadosTurma(Optional<Turma> turma) {
        return turmaRepository.saveAndFlush(turma.get());
    }

    private Turma atualizarDadosTurmaConsultada(Optional<Turma> turmaConsultada, String[] conteudoLinha) {
        return getDadosDaTurmaNaLinha(conteudoLinha, turmaConsultada);
    }

}
