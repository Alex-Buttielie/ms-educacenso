package br.com.educacenso.services.impl;

import br.com.educacenso.app.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.domains.AreaConhecimento;
import br.com.educacenso.app.domains.AreaPosGraduacao;
import br.com.educacenso.app.domains.RecursoAlunoParaAvaliacaoInep;
import br.com.educacenso.app.domains.TipoDeficienciaEspectroAltasHabilidades;
import br.com.educacenso.repositories.AreaConhecimentoRepository;
import br.com.educacenso.repositories.AreaPosGraduacaoRepository;
import br.com.educacenso.app.GenericPessoaEducation;
import br.com.educacenso.app.constraints.MaiorNivelEscolaridadeConcluido;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.constraints.TipoFiliacao;
import br.com.educacenso.app.constraints.TipoPosGraduacao;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.repositories.PessoaRepository;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.services.ExecutarImportacaoDocentesDadosPessoaisService;
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
public class ExecutarImportacaoDocentesDadosPessoaisServiceImpl
        extends GenericPessoaEducation
        implements ExecutarImportacaoDocentesDadosPessoaisService {

    public ExecutarImportacaoDocentesDadosPessoaisServiceImpl(PessoaRepository pessoaRepository,
                                                              UnidadeEnsinoRepository unidadeEnsinoRepository,
                                                              AreaConhecimentoRepository areaConhecimentoRepository,
                                                              AreaPosGraduacaoRepository areaPosGraduacaoRepository
    ) {
        super.pessoaRepository = pessoaRepository;
        super.unidadeEnsinoRepository = unidadeEnsinoRepository;
        super.areaConhecimentoRepository = areaConhecimentoRepository;
        super.areaPosGraduacaoRepository = areaPosGraduacaoRepository;
    }

    public ExecutarImportacaoDocentesDadosPessoaisServiceImpl() {
    }

    @Override
    public void importarLinhaArquivo(String[] conteudoLinha) {
        salvarPessoa(atualizarDadosPessoa(conteudoLinha));
    }
    @Override
    public Pessoa atualizarDadosPessoa(String[] conteudoLinha) {
        return rastrearPessoaCacteristicasIndiv(conteudoLinha)
                .map(pessoaConsultada -> atualizarDadosPessoaConsultada(Optional.of(pessoaConsultada), conteudoLinha))
                .orElse(atualizarDadosPessoaNaoConsultada(conteudoLinha));
    }

    @Override
    public Optional<Pessoa> rastrearPessoaCacteristicasIndiv(String[] conteudoLinha) {
        Optional<Pessoa> pessoaRastPorCpf = rastrearPessoaCpf(conteudoLinha);

        if (pessoaRastPorCpf.isPresent()) {
            return pessoaRastPorCpf;
        }

        return rastrearPessoaNome(conteudoLinha);
    }

    @Override
    public Optional<Pessoa> rastrearPessoaCpf(String[] conteudoLinha) {
        final int POSICAO_CPF = 4;
        String cpf = ofNullable(valorString(conteudoLinha, POSICAO_CPF)).orElse("");
        return pessoaRepository.findPessoaByCpf(cpf);
    }

    @Override
    public Optional<Pessoa> rastrearPessoaNome(String[] conteudoLinha) {
        return pessoaRepository.findPessoaByNome(valorString(conteudoLinha, 5));
    }

    @Override
    public Pessoa atualizarDadosPessoaConsultada(Optional<Pessoa> pessoaConsultada, String[] conteudoLinha) {
        return getDadosPessoaNaLinha(conteudoLinha, pessoaConsultada);
    }

    @Override
    public Pessoa atualizarDadosPessoaNaoConsultada(String[] conteudoLinha) {
        return getDadosPessoaNaLinha(conteudoLinha, Optional.empty());
    }
    public LocalizacaoZonaResidencia getLocalizacaoZonaResidencia(String conteudo) {
        return (LocalizacaoZonaResidencia) buscaRegistroConteudoLido(conteudo, LocalizacaoZonaResidencia.values());
    }

    public LocalizacaoDiferenciadaResidencia getLocalizacaoDiferenciadaResidencia(String conteudo) {
        return (LocalizacaoDiferenciadaResidencia) buscaRegistroConteudoLido(conteudo, LocalizacaoDiferenciadaResidencia.values());
    }

    public Nacionalidade getNacionalidade(String conteudo) {
        return (Nacionalidade) buscaRegistroConteudoLido(Nacionalidade.getValorStrPeloCodigo(conteudo), Nacionalidade.values());
    }

    public MaiorNivelEscolaridadeConcluido getMaiorNivelEscolaridadeConcluida(String conteudoLinha) {
        return MaiorNivelEscolaridadeConcluido.getTipoPorDescricao(conteudoLinha);
    }

    public RecursoAlunoParaAvaliacaoInep getRecursoAlunoParaAvaliacaoInep(String[] conteudoLinha, Optional<Pessoa> pessoaConsultadaOptional) {

        try {

            Optional<RecursoAlunoParaAvaliacaoInep> recursoAlunoParaAvaliacaoInep = pessoaConsultadaOptional
                    .map(Pessoa::getRecursoAlunoParaAvaliacaoInep)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return new RecursoAlunoParaAvaliacaoInep()
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

   //public PosGraduacaoConcluidaProfessor getPosGraduacoesConcluidasProfessor(String[] conteudoLinha, Optional<Pessoa> pessoaConsultadaOptional) {
   //    try {

   //        Optional<PosGraduacaoConcluidaProfessor> posGraduacaoConcluidaProfessorOptional = pessoaConsultadaOptional
   //                .map(Pessoa::getPosGraduacaoConcluidaProfessor)
   //                .filter(Objects::nonNull)
   //                .stream()
   //                .findAny();

   //        return new PosGraduacaoConcluidaProfessor()
   //                .builder()
   //                .id(posGraduacaoConcluidaProfessorOptional.map(PosGraduacaoConcluidaProfessor::getId).orElse(null))
   //                .tipoPosGraducacao1(getTipoPosGraduacao(conteudoLinha, 58))
   //                .anoConclusao1(stringToLong(conteudoLinha, 60))
   //                .areaPosGraduacao1(getAreaPosGraduacao(conteudoLinha, 59))
   //                .tipoPosGraducacao2(getTipoPosGraduacao(conteudoLinha, 61))
   //                .areaPosGraduacao2(getAreaPosGraduacao(conteudoLinha, 62))
   //                .anoConclusao2(stringToLong(conteudoLinha, 63))
   //                .tipoPosGraducacao3(getTipoPosGraduacao(conteudoLinha, 64))
   //                .areaPosGraduacao3(getAreaPosGraduacao(conteudoLinha, 65))
   //                .anoConclusao3(stringToLong(conteudoLinha, 66))
   //                .tipoPosGraducacao4(getTipoPosGraduacao(conteudoLinha, 67))
   //                .areaPosGraduacao4(getAreaPosGraduacao(conteudoLinha, 68))
   //                .anoConclusao4(stringToLong(conteudoLinha, 69))
   //                .tipoPosGraducacao5(getTipoPosGraduacao(conteudoLinha, 70))
   //                .areaPosGraduacao5(getAreaPosGraduacao(conteudoLinha, 71))
   //                .anoConclusao5(stringToLong(conteudoLinha, 72))
   //                .tipoPosGraducacao6(getTipoPosGraduacao(conteudoLinha, 73))
   //                .areaPosGraduacao6(getAreaPosGraduacao(conteudoLinha, 74))
   //                .anoConclusao6(stringToLong(conteudoLinha, 75))
   //                .build();

   //    } catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
   //        return null;
   //    }
   //}

    public TipoPosGraduacao getTipoPosGraduacao(String[] conteudoLinha, int posicaoConteudo) {
        try {
            return ofNullable(valorString(conteudoLinha, posicaoConteudo))
                    .map(conteudoLido->TipoPosGraduacao.values()[Integer.parseInt(conteudoLido) - 1])
                    .orElse(null);
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

   // public OutrosCursosEspecificos getOutrosCursosEspecificos(String[] conteudoLinha, Optional<Pessoa> pessoaConsultadaOptional) {
   //     try {
//
   //         Optional<OutrosCursosEspecificos> outrosCursosEspecificosOptional;
   //         outrosCursosEspecificosOptional = pessoaConsultadaOptional
   //                 .map(Pessoa::getOutrosCursosEspecificos)
   //                 .filter(Objects::nonNull)
   //                 .stream()
   //                 .findAny();
//
   //         return new OutrosCursosEspecificos()
   //                 .builder()
   //                 .id(outrosCursosEspecificosOptional.map(OutrosCursosEspecificos::getId).orElse(null))
   //                 .anosFinaisEnsinoFundamental(stringToBoolean(conteudoLinha, 80))
   //                 .anosIniciaisEnsinoFundamental(stringToBoolean(conteudoLinha, 79))
   //                 .creche(stringToBoolean(conteudoLinha, 77))
   //                 .direitoCriacaoAdolescente(stringToBoolean(conteudoLinha, 89))
   //                 .educacaoAmbiental(stringToBoolean(conteudoLinha, 86))
   //                 .educacaoCampo(stringToBoolean(conteudoLinha, 85))
   //                 .educacaoDireitosHumanos(stringToBoolean(conteudoLinha, 87))
   //                 .educacaoEspecial(stringToBoolean(conteudoLinha, 83))
   //                 .educacaoIndigena(stringToBoolean(conteudoLinha, 84))
   //                 .educacaoJovensAdultos(stringToBoolean(conteudoLinha, 82))
   //                 .educacaoRelacoesEtnicoRaciaisCulturaAfroBrasileira(stringToBoolean(conteudoLinha, 90))
   //                 .ensinoMedio(stringToBoolean(conteudoLinha, 81))
   //                 .generoDiversidadeSexual(stringToBoolean(conteudoLinha, 88))
   //                 .gestaoEscolar(stringToBoolean(conteudoLinha, 91))
   //                 .outros(stringToBoolean(conteudoLinha, 92))
   //                 .naoTemOutrosCursosEspecificos(stringToBoolean(conteudoLinha, 94))
   //                 .preEscola(stringToBoolean(conteudoLinha, 78))
   //                 .build();
//
   //     } catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
   //         return null;
   //     }
   // }

    public TipoFiliacao getTipoFiliacao(String conteudo) {
        return (TipoFiliacao) buscaRegistroConteudoLido(conteudo, TipoFiliacao.values());
    }

    //public FormacaoComplementarPedagogicaProfessor getFormacoesComplementarPedagogicaProfessor(String[] conteudoLinha,
    //                                                                                            Optional<Pessoa> pessoaConsultadaOptional) {
    //    try {
    //        Optional<FormacaoComplementarPedagogicaProfessor> formacaoComplementarPedagogicaProfessorOptional = pessoaConsultadaOptional
    //                .map(Pessoa::getFormacaoComplementarPedagogicaProfessor)
    //                .filter(Objects::nonNull)
    //                .stream()
    //                .findAny();
//
    //        return new FormacaoComplementarPedagogicaProfessor()
    //                .builder()
    //                .id(formacaoComplementarPedagogicaProfessorOptional.map(FormacaoComplementarPedagogicaProfessor::getId).orElse(null))
    //                .areaConhecimentoComponentesCurriculares1(getAreaConhecimentoCurricular(conteudoLinha, 55))
    //                .areaConhecimentoComponentesCurriculares2(getAreaConhecimentoCurricular(conteudoLinha, 56))
    //                .areaConhecimentoComponentesCurriculares3(getAreaConhecimentoCurricular(conteudoLinha, 57))
    //                .build();
//
    //    }catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
    //        return null;
    //    }
//
    //}

    public TipoDeficienciaEspectroAltasHabilidades getTipoDeficienciaEspectroAltasHabilidades(String[] conteudoLinha, Optional<Pessoa> pessoaConsultadaOptional) {
        try {

            Optional<TipoDeficienciaEspectroAltasHabilidades> tipoDeficienciaEspectroAltasHabilidadesOptional = pessoaConsultadaOptional
                    .map(Pessoa::getTipoDeficienciaEspectroAltasHabilidades)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return new TipoDeficienciaEspectroAltasHabilidades()
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
        return ofNullable(conteudoLinha[posicaoCampo])
                .filter(conteudoPosicao -> !conteudoPosicao.isEmpty())
                .map(isConteudoCampoPreenchido -> areaConhecimentoRepository.findById(Long.parseLong(conteudoLinha[posicaoCampo])).get())
                .orElse(null);
    }
    public Pessoa getDadosPessoaNaLinha(String[] conteudoLinha, Optional<Pessoa> pessoaConsultadaOptional) {
        return new Pessoa()
                .builder()
                .id(pessoaConsultadaOptional.map(Pessoa::getId).orElse(null))
                .fkUni(unidadeEnsinoRepository.findById(52104346l).orElse(null))
                .identificacaoUnica(stringToLong(conteudoLinha, 3))
                .cep(valorString(conteudoLinha, 40))
                .corRaca(getCorRaca(conteudoLinha, 11))
                .cpf(valorString(conteudoLinha, 4))
                .dataNascimento(stringToDate(conteudoLinha, 6))
                .localizacaoDiferenciadaResidencia(getLocalizacaoDiferenciadaResidencia(valorString(conteudoLinha, 43)))
                .localizacaoZonaResidencia(getLocalizacaoZonaResidencia(valorString(conteudoLinha, 42)))
                .nacionalidade(getNacionalidade(valorString(conteudoLinha, 12)))
                .tipoFiliacao(getTipoFiliacao(valorString(conteudoLinha, 7)))
                .nome(valorString(conteudoLinha, 5))
                .nomeMae(valorString(conteudoLinha, 8))
               // .maiorNivelEscolaridadeConcluido(getMaiorNivelEscolaridadeConcluida(valorString(conteudoLinha, 44)))
                //.tipoEnsinoMedioCursado(getTipoEnsinoMedioCursado(valorString(conteudoLinha, 45)))
                .numeroMatriculaCertidaoNascimento(valorString(conteudoLinha, 38))
                .paisNacionalidade(getPais(stringToInteger(conteudoLinha, 13)))
                .paisResidencia(getPais(stringToInteger(conteudoLinha, 39)))
                .sexo(getSexo(stringToInteger(conteudoLinha, 10)))
                //.tipoEnsinoMedioCursado(getTipoEnsinoMedioCursado(valorString(conteudoLinha, 45)))
                .tipoRegistro(getTipoRegistro(valorString(conteudoLinha, 0)))
                .enderecoEletronicoEmail("campo93naoesquecer@gmail.com")
                //.naoTemPosGraduacaoConcluida(stringToBoolean(conteudoLinha, 76))
                //.formacaoComplementarPedagogicaProfessor(getFormacoesComplementarPedagogicaProfessor(conteudoLinha, pessoaConsultadaOptional))
                //.outrosCursosEspecificos(getOutrosCursosEspecificos(conteudoLinha, pessoaConsultadaOptional))
                .tipoDeficienciaEspectroAltasHabilidades(getTipoDeficienciaEspectroAltasHabilidades(conteudoLinha, pessoaConsultadaOptional))
                .recursoAlunoParaAvaliacaoInep(getRecursoAlunoParaAvaliacaoInep(conteudoLinha, pessoaConsultadaOptional))
                //.municipioNascimento(conteudoLinha[14])
                //.municipioResidencia(conteudoLinha[41])
                //.posGraduacaoConcluidaProfessor(getPosGraduacoesConcluidasProfessor(conteudoLinha, pessoaConsultadaOptional))
                .build();

    }


}
