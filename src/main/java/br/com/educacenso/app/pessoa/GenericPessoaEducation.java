package br.com.educacenso.app.pessoa;

import br.com.educacenso.app.docente.repositorys.AreaConhecimentoRepository;
import br.com.educacenso.app.docente.repositorys.AreaPosGraduacaoRepository;
import br.com.educacenso.app.docente.repositorys.FormacaoComplementarPedagogicaProfessorRepository;
import br.com.educacenso.app.docente.repositorys.OutrosCursosEspecificosRepository;
import br.com.educacenso.app.docente.repositorys.PosGraduacaoConcluidaProfessorRepository;
import br.com.educacenso.app.docente.repositorys.TipoDeficienciaEspectroAltasHabilidadesRepository;
import br.com.educacenso.app.pessoa.constraints.CorRaca;
import br.com.educacenso.app.pessoa.constraints.Sexo;
import br.com.educacenso.app.pessoa.domains.Pessoa;
import br.com.educacenso.app.pessoa.repositorys.PessoaRepository;
import br.com.educacenso.app.unidadeEnsino.repositorys.RecursoAlunoParaAvaliacaoInepRepository;
import br.com.educacenso.app.unidadeEnsino.repositorys.UnidadeEnsinoRepository;
import br.com.educacenso.architecture.GenericEducation;
import br.com.educacenso.contraints.Paises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Qualifier("genericPessoaEducation")
@Component
public abstract class GenericPessoaEducation extends GenericEducation {

    @Autowired
    protected PessoaRepository pessoaRepository;

    @Autowired
    protected UnidadeEnsinoRepository unidadeEnsinoRepository;

    @Autowired
    protected AreaConhecimentoRepository areaConhecimentoRepository;

    @Autowired
    protected AreaPosGraduacaoRepository areaPosGraduacaoRepository;

    @Autowired
    protected FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository;

    @Autowired
    protected TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository;

    @Autowired
    protected OutrosCursosEspecificosRepository outrosCursosEspecificosRepository;

    @Autowired
    protected RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository;
    @Autowired
    protected PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository;

    protected Pessoa pessoaParaSalvar;

    protected abstract Pessoa atualizarDadosPessoa(String [] conteudoLinha);
    protected abstract Pessoa atualizarDadosPessoaConsultada(Optional<Pessoa> pessoaConsultada, String[] conteudoLinha);
    protected abstract Pessoa atualizarDadosPessoaNaoConsultada(String[] conteudoLinha);

    protected CorRaca getCorRaca(String[] conteudoLinha, int posicaoConteudo) {
        try {
            return Optional
                    .ofNullable(valorString(conteudoLinha, posicaoConteudo))
                    .map(conteudoLido->CorRaca.values()[Integer.parseInt(conteudoLido)])
                    .orElse(null);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return null;
        }
    }

    protected Sexo getSexo(Integer codigoSexo) {
        return Optional.ofNullable(codigoSexo).map(getFunctionSexo()).orElse(null);
    }
    protected Paises getPais(Integer codigoPais) {
        return Optional.ofNullable(codigoPais).map(getFunctionPaisPorDescricao()).orElse(null);
    }

    private Function<Integer, Paises> getFunctionPaisPorDescricao() {
        return codigoPais -> Paises.getValorPorDescricao(codigoPais);
    }

    private Function<Integer, Sexo> getFunctionSexo() {
        return codigoSexo -> Sexo.values()[codigoSexo -1];
    }

    public void salvarPessoa(Pessoa pessoaParaSalvar) {
        Optional.ofNullable(pessoaParaSalvar).ifPresent(pessoa -> pessoaRepository.save(pessoa));
        limparPessoaScopo();
    }

    protected void limparPessoaScopo() {
        pessoaParaSalvar = new Pessoa();
    }

}


