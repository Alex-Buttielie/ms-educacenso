package br.com.educacenso.app.services.impl;

import br.com.educacenso.app.constraints.MaiorNivelEscolaridadeConcluido;
import br.com.educacenso.app.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.constraints.TipoPosGraduacao;
import br.com.educacenso.app.domains.AreaConhecimento;
import br.com.educacenso.app.domains.AreaPosGraduacao;
import br.com.educacenso.app.domains.FormacaoComplementarPedagogicaProfessor;
import br.com.educacenso.app.domains.OutrosCursosEspecificos;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.PosGraduacaoConcluidaProfessor;
import br.com.educacenso.app.domains.Professor;
import br.com.educacenso.app.domains.RecursoAlunoParaAvaliacaoInep;
import br.com.educacenso.app.domains.TipoDeficienciaEspectroAltasHabilidades;
import br.com.educacenso.app.services.ExecutarImportacaoDocentesDadosPessoaisService;
import br.com.educacenso.app.repositories.AreaConhecimentoRepository;
import br.com.educacenso.app.repositories.AreaPosGraduacaoRepository;
import br.com.educacenso.app.repositories.FormacaoComplementarPedagogicaProfessorRepository;
import br.com.educacenso.app.repositories.OutrosCursosEspecificosRepository;
import br.com.educacenso.app.repositories.PessoaRepository;
import br.com.educacenso.app.repositories.PosGraduacaoConcluidaProfessorRepository;
import br.com.educacenso.app.repositories.ProfessorRepository;
import br.com.educacenso.app.repositories.RecursoAlunoParaAvaliacaoInepRepository;
import br.com.educacenso.app.repositories.TipoDeficienciaEspectroAltasHabilidadesRepository;
import br.com.educacenso.app.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Alex Buttielie Alves Guimarães
 *
 * Service responsável por realizar a importação dos dados pessoais
 * do docente identificado na linha do arquivo .TXT durante a importação do educacenso
 *
 */
@Qualifier("docenteDadosPessoais")
@Service
public class ExecutarImportacaoDocentesDadosPessoaisServiceImplService
        extends GenericServicePessoaImportacao
        implements ExecutarImportacaoDocentesDadosPessoaisService {

    protected ProfessorRepository repository;
    protected AreaConhecimentoRepository areaConhecimentoRepository;

    protected AreaPosGraduacaoRepository areaPosGraduacaoRepository;

    protected FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository;

    protected TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository;

    protected OutrosCursosEspecificosRepository outrosCursosEspecificosRepository;

    protected RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository;
    protected PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository;
    protected MessageProducer messageProducer;

    @Autowired
    public ExecutarImportacaoDocentesDadosPessoaisServiceImplService(PessoaRepository pessoaRepository,
                                                                     UnidadeEnsinoRepository unidadeEnsinoRepository,
                                                                     AreaConhecimentoRepository areaConhecimentoRepository,
                                                                     AreaPosGraduacaoRepository areaPosGraduacaoRepository,
                                                                     FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository,
                                                                     TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository,
                                                                     OutrosCursosEspecificosRepository outrosCursosEspecificosRepository,
                                                                     RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository,
                                                                     PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository,
                                                                     ProfessorRepository professorRepository,
                                                                     MessageProducer messageProducer) {
        super(pessoaRepository,unidadeEnsinoRepository, messageProducer);
        this.messageProducer = messageProducer;
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
        this.areaConhecimentoRepository = areaConhecimentoRepository;
        this.areaPosGraduacaoRepository = areaPosGraduacaoRepository;
        this.formacaoComplementarPedagogicaProfessorRepository = formacaoComplementarPedagogicaProfessorRepository;
        this.tipoDeficienciaEspectroAltasHabilidadesRepository = tipoDeficienciaEspectroAltasHabilidadesRepository;
        this.outrosCursosEspecificosRepository = outrosCursosEspecificosRepository;
        this.recursoAlunoParaAvaliacaoInepRepository = recursoAlunoParaAvaliacaoInepRepository;
        this.posGraduacaoConcluidaProfessorRepository = posGraduacaoConcluidaProfessorRepository;
        this.repository = professorRepository;
    }

    @Override
    public Optional<Professor> importarLinhaArquivo(String[] conteudoLinha) {
        var pessoa = salvarPessoa(atualizarDadosPessoa(conteudoLinha));
        var professor = atualizarDadosProfessor(conteudoLinha, pessoa);
        salvarPessoa(atualizarDadosPessoa(conteudoLinha));
        return Optional.of(repository.save(professor));
    }

    public Professor atualizarDadosProfessor(String[] conteudoLinha, Pessoa pessoa) {
        return rastrearProfessor(pessoa)
                .map(professorConsultado -> atualizarDadosProfessorConsultado(Optional.of(professorConsultado), conteudoLinha, pessoa))
                .orElse(atualizarDadosProfessorNaoConsultado(conteudoLinha, pessoa));
    }

    public Optional<Professor> rastrearProfessor(Pessoa pessoa) {
        return repository.findProfessorByPessoa(pessoa);
    }

    protected Professor atualizarDadosProfessorNaoConsultado(String[] conteudoLinha, Pessoa pessoa) {
        return getDadosProfessorNaLinha(conteudoLinha, Optional.empty(), pessoa);
    }

    protected Professor atualizarDadosProfessorConsultado(Optional<Professor> professorConsultado,
                                                        String[] conteudoLinha, Pessoa pessoa) {
        return getDadosProfessorNaLinha(conteudoLinha, professorConsultado, pessoa);
    }

    protected Professor getDadosProfessorNaLinha(String[] conteudoLinha,
                                               Optional<Professor> professor,
                                               Pessoa pessoa) {
        return  Professor
                .builder()
                .id(professor.map(Professor::getId).orElse(null))
                .pessoa(pessoa)
                .maiorNivelEscolaridadeConcluido(getMaiorNivelEscolaridadeConcluida(valorString(conteudoLinha, 44)))
                .naoTemPosGraduacaoConcluida(stringToBoolean(conteudoLinha, 76))
                .formacaoComplementarPedagogicaProfessor(getFormacoesComplementarPedagogicaProfessor(conteudoLinha, professor))
                .outrosCursosEspecificos(getOutrosCursosEspecificos(conteudoLinha, professor))
                .tipoDeficienciaEspectroAltasHabilidades(getTipoDeficienciaEspectroAltasHabilidades(conteudoLinha, professor))
                .tipoEnsinoMedioCursado(getTipoEnsinoMedioCursado(valorString(conteudoLinha, 45)))
                .recursoAlunoParaAvaliacaoInep(getRecursoAlunoParaAvaliacaoInep(conteudoLinha, professor))
                .posGraduacaoConcluidaProfessor(getPosGraduacoesConcluidasProfessor(conteudoLinha, professor))
                .naoTemPosGraduacaoConcluida(stringToBoolean(conteudoLinha, 76))
                .formacaoComplementarPedagogicaProfessor(getFormacoesComplementarPedagogicaProfessor(conteudoLinha, professor))
                .outrosCursosEspecificos(getOutrosCursosEspecificos(conteudoLinha, professor))
                .tipoDeficienciaEspectroAltasHabilidades(getTipoDeficienciaEspectroAltasHabilidades(conteudoLinha, professor))
                 .recursoAlunoParaAvaliacaoInep(getRecursoAlunoParaAvaliacaoInep(conteudoLinha, professor))
                .posGraduacaoConcluidaProfessor(getPosGraduacoesConcluidasProfessor(conteudoLinha, professor))
                .build();
    }

    public MaiorNivelEscolaridadeConcluido getMaiorNivelEscolaridadeConcluida(String conteudoLinha) {
        return MaiorNivelEscolaridadeConcluido.getTipoPorDescricao(conteudoLinha);
    }

    public RecursoAlunoParaAvaliacaoInep getRecursoAlunoParaAvaliacaoInep(String[] conteudoLinha, Optional<Professor> professorConsultadaOptional) {

        try {

            var recursoAlunoParaAvaliacaoInep = professorConsultadaOptional
                    .map(Professor::getRecursoAlunoParaAvaliacaoInep)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  RecursoAlunoParaAvaliacaoInep
                    .builder()
                    .id(recursoAlunoParaAvaliacaoInep.map(RecursoAlunoParaAvaliacaoInep::getId).orElse(null))
                    .auxilioLedor(stringToBoolean(conteudoLinha, 26))
                    .auxilioTranscricao(stringToBoolean(conteudoLinha, 27))
                    .codigoAudioDeficienteVisual(stringToBoolean(conteudoLinha, 33))
                    .guiaInterprete(stringToBoolean(conteudoLinha, 28))
                    .leituraLabial(stringToBoolean(conteudoLinha, 30))
                    .materiaDidaticoProvaBraille(stringToBoolean(conteudoLinha, 36))
                    .provaAmpliadaFonte18(stringToBoolean(conteudoLinha, 31))
                    .provaLinguaPortuguesaSegundaLinguaSurdosDeficientesAuditivos(stringToBoolean(conteudoLinha, 34))
                    .provaSuperAmpliadaFonte24(stringToBoolean(conteudoLinha, 32))
                    .provaVideosLibras(stringToBoolean(conteudoLinha, 35))
                    .tradutorInterpreteLibras(stringToBoolean(conteudoLinha, 29))
                    .nenhum(stringToBoolean(conteudoLinha, 37))
                    .build();

        } catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }

    }

    public TipoEnsinoMedioCursado getTipoEnsinoMedioCursado(String conteudo) {
        return (TipoEnsinoMedioCursado) buscaRegistroConteudoLido(TipoEnsinoMedioCursado.getValorStrPeloCodigo(conteudo), TipoEnsinoMedioCursado.values());
    }

    public PosGraduacaoConcluidaProfessor getPosGraduacoesConcluidasProfessor(String[] conteudoLinha, Optional<Professor> professorConsultadaOptional) {
        try {
            var posGraduacaoConcluidaProfessorOptional = professorConsultadaOptional
                    .map(Professor::getPosGraduacaoConcluidaProfessor)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  PosGraduacaoConcluidaProfessor
                    .builder()
                    .id(posGraduacaoConcluidaProfessorOptional.map(PosGraduacaoConcluidaProfessor::getId).orElse(null))
                    .tipoPosGraducacao1(getTipoPosGraduacao(conteudoLinha, 58))
                    .anoConclusao1(stringToLong(conteudoLinha, 60))
                    .areaPosGraduacao1(getAreaPosGraduacao(conteudoLinha, 59))
                    .tipoPosGraducacao2(getTipoPosGraduacao(conteudoLinha, 61))
                    .areaPosGraduacao2(getAreaPosGraduacao(conteudoLinha, 62))
                    .anoConclusao2(stringToLong(conteudoLinha, 63))
                    .tipoPosGraducacao3(getTipoPosGraduacao(conteudoLinha, 64))
                    .areaPosGraduacao3(getAreaPosGraduacao(conteudoLinha, 65))
                    .anoConclusao3(stringToLong(conteudoLinha, 66))
                    .tipoPosGraducacao4(getTipoPosGraduacao(conteudoLinha, 67))
                    .areaPosGraduacao4(getAreaPosGraduacao(conteudoLinha, 68))
                    .anoConclusao4(stringToLong(conteudoLinha, 69))
                    .tipoPosGraducacao5(getTipoPosGraduacao(conteudoLinha, 70))
                    .areaPosGraduacao5(getAreaPosGraduacao(conteudoLinha, 71))
                    .anoConclusao5(stringToLong(conteudoLinha, 72))
                    .tipoPosGraducacao6(getTipoPosGraduacao(conteudoLinha, 73))
                    .areaPosGraduacao6(getAreaPosGraduacao(conteudoLinha, 74))
                    .anoConclusao6(stringToLong(conteudoLinha, 75))
                    .build();
        } catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }
    }

    public TipoPosGraduacao getTipoPosGraduacao(String[] conteudoLinha, int posicaoConteudo) {
        try {
            return (TipoPosGraduacao) buscaRegistroConteudoLido(TipoPosGraduacao.getValorStrPeloCodigo(conteudoLinha[posicaoConteudo]), TipoPosGraduacao.values());
        }catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }
    }

    public AreaPosGraduacao getAreaPosGraduacao(String[] conteudoLinha, int posicaoConteudo) {
        try {
            return  ofNullable(stringToLong(conteudoLinha, posicaoConteudo))
                    .map(codigo -> areaPosGraduacaoRepository.findById(codigo).orElse(null))
                    .orElse(null);
        }catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }
    }

    public OutrosCursosEspecificos getOutrosCursosEspecificos(String[] conteudoLinha, Optional<Professor> professorConsultadaOptional) {
        try {

            var outrosCursosEspecificosOptional = professorConsultadaOptional
                    .map(Professor::getOutrosCursosEspecificos)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  OutrosCursosEspecificos
                    .builder()
                    .id(outrosCursosEspecificosOptional.map(OutrosCursosEspecificos::getId).orElse(null))
                    .anosFinaisEnsinoFundamental(stringToBoolean(conteudoLinha, 80))
                    .anosIniciaisEnsinoFundamental(stringToBoolean(conteudoLinha, 79))
                    .creche(stringToBoolean(conteudoLinha, 77))
                    .direitoCriacaoAdolescente(stringToBoolean(conteudoLinha, 89))
                    .educacaoAmbiental(stringToBoolean(conteudoLinha, 86))
                    .educacaoCampo(stringToBoolean(conteudoLinha, 85))
                    .educacaoDireitosHumanos(stringToBoolean(conteudoLinha, 87))
                    .educacaoEspecial(stringToBoolean(conteudoLinha, 83))
                    .educacaoIndigena(stringToBoolean(conteudoLinha, 84))
                    .educacaoJovensAdultos(stringToBoolean(conteudoLinha, 82))
                    .educacaoRelacoesEtnicoRaciaisCulturaAfroBrasileira(stringToBoolean(conteudoLinha, 90))
                    .ensinoMedio(stringToBoolean(conteudoLinha, 81))
                    .generoDiversidadeSexual(stringToBoolean(conteudoLinha, 88))
                    .gestaoEscolar(stringToBoolean(conteudoLinha, 91))
                    .outros(stringToBoolean(conteudoLinha, 92))
                    .naoTemOutrosCursosEspecificos(stringToBoolean(conteudoLinha, 94))
                    .preEscola(stringToBoolean(conteudoLinha, 78))
                    .build();

        } catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }
    }

    public FormacaoComplementarPedagogicaProfessor getFormacoesComplementarPedagogicaProfessor(String[] conteudoLinha,
                                                                                               Optional<Professor> professorConsultadoOptional) {
        try {
            var formacaoComplementarPedagogicaProfessorOptional = professorConsultadoOptional
                    .map(Professor::getFormacaoComplementarPedagogicaProfessor)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  FormacaoComplementarPedagogicaProfessor
                    .builder()
                    .id(formacaoComplementarPedagogicaProfessorOptional.map(FormacaoComplementarPedagogicaProfessor::getId).orElse(null))
                    .areaConhecimentoComponentesCurriculares1(getAreaConhecimentoCurricular(conteudoLinha, 55))
                    .areaConhecimentoComponentesCurriculares2(getAreaConhecimentoCurricular(conteudoLinha, 56))
                    .areaConhecimentoComponentesCurriculares3(getAreaConhecimentoCurricular(conteudoLinha, 57))
                    .build();

        }catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }

    }

    public TipoDeficienciaEspectroAltasHabilidades getTipoDeficienciaEspectroAltasHabilidades(String[] conteudoLinha, Optional<Professor> professorConsultadoOptional) {
        try {
            var tipoDeficienciaEspectroAltasHabilidadesOptional = professorConsultadoOptional
                    .map(Professor::getTipoDeficienciaEspectroAltasHabilidades)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  TipoDeficienciaEspectroAltasHabilidades
                    .builder()
                    .id(tipoDeficienciaEspectroAltasHabilidadesOptional.map(TipoDeficienciaEspectroAltasHabilidades::getId).orElse(null))
                    .altasHabilidadesSuperdotacao(stringToBoolean(conteudoLinha, 25))
                    .baixaVisao(stringToBoolean(conteudoLinha, 17))
                    .cegueira(stringToBoolean(conteudoLinha, 16))
                    .deficienciaAuditiva(stringToBoolean(conteudoLinha, 20))
                    .deficienciaFisica(stringToBoolean(conteudoLinha, 21))
                    .deficienciaIntectual(stringToBoolean(conteudoLinha, 12))
                    .deficienciaMultipla(stringToBoolean(conteudoLinha, 23))
                    .surdez(stringToBoolean(conteudoLinha, 18))
                    .surdoCegueira(stringToBoolean(conteudoLinha, 21))
                    .transtornoEspectroAutista(stringToBoolean(conteudoLinha, 23))
                    .pessoaComDeficienciaEspectroAutistaSuperdotacao(stringToBoolean(conteudoLinha, 25))
                    .build();
        } catch (ArrayIndexOutOfBoundsException  | NumberFormatException | UnsupportedOperationException e) {
            return null;
        }
    }

    public AreaConhecimento getAreaConhecimentoCurricular(String[] conteudoLinha, int posicaoCampo) {
        try {
            return  ofNullable(stringToLong(conteudoLinha, posicaoCampo))
                    .map(valor -> areaConhecimentoRepository.findById(valor).orElse(null))
                    .orElse(null);

        }catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }
    }


}
