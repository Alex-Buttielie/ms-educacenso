package br.com.educacenso.app;

import br.com.educacenso.app.constraints.CorRaca;
import br.com.educacenso.app.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.constraints.Sexo;
import br.com.educacenso.app.constraints.TipoFiliacao;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.architecture.GenericEducacensoImportacao;
import br.com.educacenso.contraints.Paises;
import br.com.educacenso.repositories.PessoaRepository;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.services.GenericPessoaImportacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

/**
 * @author Alex Buttielie Alves Guimarães
 *
 * Classe abastrata retentora dos métodos genéricos usados na manipulação dos dados lidos durante a importação
 * para o Objeto Pessoa que será eventualmente persistido no banco de dados. Também será responsável por manter
 * os métodos genéricos envolvendo a entidade Pessoa.
 *
 */

@Component
@Primary
public class GenericPessoaImportacao extends GenericEducacensoImportacao implements GenericPessoaImportacaoService {

    protected  PessoaRepository pessoaRepository;
    protected UnidadeEnsinoRepository unidadeEnsinoRepository;

    protected Pessoa pessoaParaSalvar;

    @Autowired
    protected GenericPessoaImportacao(final PessoaRepository pessoaRepository,
                                      final UnidadeEnsinoRepository unidadeEnsinoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
    }

    public GenericPessoaImportacao() {
    }


    protected static CorRaca getCorRaca(String[] conteudoLinha, int posicaoConteudo) {
        try {
            return Optional
                    .ofNullable(valorString(conteudoLinha, posicaoConteudo))
                    .map(conteudoLido->CorRaca.values()[Integer.parseInt(conteudoLido)])
                    .orElse(null);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return null;
        }
    }

    protected static Sexo getSexo(Integer codigoSexo) {
        return Optional.ofNullable(codigoSexo).map(getFunctionSexo()).orElse(null);
    }
    protected static Paises getPais(Integer codigoPais) {
        return Optional.ofNullable(codigoPais).map(getFunctionPaisPorDescricao()).orElse(null);
    }

    private static Function<Integer, Paises> getFunctionPaisPorDescricao() {
        return codigoPais -> Paises.getValorPorDescricao(codigoPais);
    }

    private static Function<Integer, Sexo> getFunctionSexo() {
        return codigoSexo -> Sexo.values()[codigoSexo -1];
    }

    public void salvarPessoa(Pessoa pessoaParaSalvar) {
        Optional.ofNullable(pessoaParaSalvar).ifPresent(pessoa -> pessoaRepository.save(pessoa));
        limparPessoaScopo();
    }

    protected void limparPessoaScopo() {
        pessoaParaSalvar = new Pessoa();
    }

    @Override
    public Pessoa atualizarDadosPessoa(String[] conteudoLinha) {
        return rastrearPessoaCacteristicasIndiv(conteudoLinha)
                .map(pessoaConsultada -> atualizarDadosPessoaConsultada(Optional.of(pessoaConsultada), conteudoLinha))
                .orElse(atualizarDadosPessoaNaoConsultada(conteudoLinha));
    }

    public Optional<Pessoa> rastrearPessoaCacteristicasIndiv(String[] conteudoLinha) {
        Optional<Pessoa> pessoaRastPorCpf = rastrearPessoaCpf(conteudoLinha);

        if (pessoaRastPorCpf.isPresent()) {
            return pessoaRastPorCpf;
        }

        return rastrearPessoaNome(conteudoLinha);
    }

    public Optional<Pessoa> rastrearPessoaCpf(String[] conteudoLinha) {
        final int POSICAO_CPF = 4;
        String cpf = ofNullable(valorString(conteudoLinha, POSICAO_CPF)).orElse("");
        return pessoaRepository.findPessoaByCpf(cpf);
    }

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
                //.tipoDeficienciaEspectroAltasHabilidades(getTipoDeficienciaEspectroAltasHabilidades(conteudoLinha, pessoaConsultadaOptional))
                // .recursoAlunoParaAvaliacaoInep(getRecursoAlunoParaAvaliacaoInep(conteudoLinha, pessoaConsultadaOptional))
                //.municipioNascimento(conteudoLinha[14])
                //.municipioResidencia(conteudoLinha[41])
                //.posGraduacaoConcluidaProfessor(getPosGraduacoesConcluidasProfessor(conteudoLinha, pessoaConsultadaOptional))
                .build();

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

    public TipoFiliacao getTipoFiliacao(String conteudo) {
        return (TipoFiliacao) buscaRegistroConteudoLido(conteudo, TipoFiliacao.values());
    }

}


