package br.com.educacenso.app.services.impl;

import br.com.educacenso.EducacensoApplicationTest;
import br.com.educacenso.app.constraints.MaiorNivelEscolaridadeConcluido;
import br.com.educacenso.app.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.constraints.TipoPosGraduacao;
import br.com.educacenso.app.domains.AreaConhecimento;
import br.com.educacenso.app.domains.AreaPosGraduacao;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.Professor;
import br.com.educacenso.app.repositories.AreaConhecimentoRepository;
import br.com.educacenso.app.repositories.AreaPosGraduacaoRepository;
import br.com.educacenso.app.repositories.PessoaRepository;
import br.com.educacenso.app.repositories.ProfessorRepository;
import br.com.educacenso.app.repositories.UnidadeEnsinoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExecutarImportacaoDocentesDadosPessoaisServiceImplTest extends EducacensoApplicationTest {

    @InjectMocks
    private ExecutarImportacaoDocentesDadosPessoaisServiceImplService executarImportacaoDocentesDadosPessoaisService;
    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private AreaConhecimentoRepository areaConhecimentoRepository;
    @Mock
    private UnidadeEnsinoRepository unidadeEnsinoRepository;
    @Mock
    private ProfessorRepository professorRepository;
    private Optional<Professor> professor;
    @Mock
    private AreaPosGraduacaoRepository areaPosGraduacaoRepository;
    @Mock
    protected Pessoa pessoa;

    @BeforeEach
    public void init() {
        pessoa = new Pessoa().builder()
                .nome("ANGELA MARIA TEIXEIRA MARTINS")
                .cpf("78657369168")
                .enderecoEletronicoEmail("campo93naoesquecer@gmail.com")
                .build();

        professor = Optional.of(new Professor().builder().id(1l)
                .build());

        executarImportacaoDocentesDadosPessoaisService = new ExecutarImportacaoDocentesDadosPessoaisServiceImplService(pessoaRepository, unidadeEnsinoRepository, areaConhecimentoRepository,
                areaPosGraduacaoRepository, null,
                null, null,
                null,null, professorRepository);
    }

    @Test
    public void naoDeveTestarImportarLinha(){
        assertThrows(Exception.class, ()-> executarImportacaoDocentesDadosPessoaisService.importarLinhaArquivo(null));
    }

    @Test
    public void deveTestarImportarLinha () {
        when(pessoaRepository.findPessoaByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any())).thenReturn(pessoa);
        when(professorRepository.findProfessorByPessoa(any())).thenReturn(professor);
        when(professorRepository.save(any())).thenReturn(professor.get());
        var retorno = executarImportacaoDocentesDadosPessoaisService.importarLinhaArquivo(NOVOS_DADOS_PESSOA);
        assertNotNull(retorno.get());
    }

    @Test
    public void deveRastrearProfessor() {
        when(professorRepository.findProfessorByPessoa(pessoa)).thenReturn(professor);
        var retorno = executarImportacaoDocentesDadosPessoaisService.rastrearProfessor(pessoa).orElse(null);
        assertNotNull(retorno);
    }

    @Test
    public void deveAtualizarDadosProfessorNaoConsultada() {
        var professorConsulta = executarImportacaoDocentesDadosPessoaisService.atualizarDadosProfessorNaoConsultado(DADOS_PESSOA_VAZIO_STR, pessoa);
        var professorConsultaDadosPreenchidos = executarImportacaoDocentesDadosPessoaisService.atualizarDadosPessoaNaoConsultada(NOVOS_DADOS_PESSOA);
        assertNotNull(professorConsulta);
        assertNotNull(professorConsultaDadosPreenchidos);
    }

    @Test
    public void deveAtualizarDadosProfessorConsultada() {
        var retorno = executarImportacaoDocentesDadosPessoaisService.atualizarDadosProfessorConsultado(professor, NOVOS_DADOS_PESSOA, pessoa);
        assertNotNull(retorno);
        assertNotNull(retorno.getId());
        assertEquals(retorno.getPessoa(), pessoa);
    }

    @Test
    public void deveBuscarDadosProfessorNaLinha() {
        var retorno = executarImportacaoDocentesDadosPessoaisService.getDadosProfessorNaLinha(NOVOS_DADOS_PESSOA, professor, pessoa);
        assertNotNull(retorno);
    }

    @Test
    public void deveBuscarMaiorNivelEscolaridadeConcluida() {
        final var CODIGO_NIVEL = MaiorNivelEscolaridadeConcluido.ENSINO_MEDIO.getDescricao();
        MaiorNivelEscolaridadeConcluido nivel = executarImportacaoDocentesDadosPessoaisService.getMaiorNivelEscolaridadeConcluida(CODIGO_NIVEL);
        assertEquals( MaiorNivelEscolaridadeConcluido.ENSINO_MEDIO, nivel);
    }

    @Test
    public void deveBuscarRecursoAlunoParaAvaliacaoInep() {
        var recursoAlunoParaAvaliacaoInep = executarImportacaoDocentesDadosPessoaisService.getRecursoAlunoParaAvaliacaoInep(NOVOS_DADOS_PESSOA, professor);
        Assert.assertNotNull(recursoAlunoParaAvaliacaoInep);
    }

    @Test
    public void deveBuscarTipoEnsinoMedioCursado() {
        final var CODIGO_ENSINO_MEDIO_CURSADO = String.valueOf(TipoEnsinoMedioCursado.CURSO_TECNICO.getCodigoEducacenso());
        TipoEnsinoMedioCursado ensinoMedioCursado = executarImportacaoDocentesDadosPessoaisService.getTipoEnsinoMedioCursado(CODIGO_ENSINO_MEDIO_CURSADO);
        assertEquals(TipoEnsinoMedioCursado.CURSO_TECNICO, ensinoMedioCursado);
    }

    @Test
    public void deveBuscarPosGraduacoesConcluidasProfessor() {
        var posGraduacaoConcluidaProfessor = executarImportacaoDocentesDadosPessoaisService.getPosGraduacoesConcluidasProfessor(NOVOS_DADOS_PESSOA, professor);
        Assert.assertNotNull(posGraduacaoConcluidaProfessor);
    }

    @Test
    public void deveBuscarTipoPosGraduacao() {
        var posGraduacaoConcluidaProfessor = executarImportacaoDocentesDadosPessoaisService.getTipoPosGraduacao(NOVOS_DADOS_PESSOA, 64);
        Assert.assertNotNull(posGraduacaoConcluidaProfessor);
        Assert.assertEquals(TipoPosGraduacao.ESPECIALIZACAO, posGraduacaoConcluidaProfessor);
    }

    @Test
    public void naoDeveBuscarTipoPosGraduacao() {
        assertThrows(Exception.class, () -> executarImportacaoDocentesDadosPessoaisService.getTipoPosGraduacao(null, 64));
    }

    @Test
    public void deveBuscarAreaPosGraduacao() {
        when(areaPosGraduacaoRepository.findById(any())).thenReturn(Optional.of(AreaPosGraduacao.builder().codigo(1l).build()));
        var areaPosGraduacao = executarImportacaoDocentesDadosPessoaisService.getAreaPosGraduacao(NOVOS_DADOS_PESSOA, 62);
        Assert.assertNotNull(areaPosGraduacao);
    }

    @Test
    public void deveBuscarOutrosCursosEspecificos() {
        var outrosCursosEspecificos = executarImportacaoDocentesDadosPessoaisService.getOutrosCursosEspecificos(NOVOS_DADOS_PESSOA, professor);
        Assert.assertNotNull(outrosCursosEspecificos);
    }

    @Test
    public void deveBuscarFormacoesComplementarPedagogicaProfessor() {
        var formacaoComplementarPedagogicaProfessor = executarImportacaoDocentesDadosPessoaisService.getFormacoesComplementarPedagogicaProfessor(NOVOS_DADOS_PESSOA, professor);
        Assert.assertNotNull(formacaoComplementarPedagogicaProfessor);
    }

    @Test
    public void deveBuscarTipoDeficienciaEspectroAltasHabilidades() {
        var tipoDeficienciaEspectroAltasHabilidades = executarImportacaoDocentesDadosPessoaisService.getTipoDeficienciaEspectroAltasHabilidades(NOVOS_DADOS_PESSOA, professor);
        Assert.assertNotNull(tipoDeficienciaEspectroAltasHabilidades);
    }

    @Test
    public void deveBuscarAreaConhecimentoCurricular() {
        when(areaConhecimentoRepository.findById(1l)).thenReturn(Optional.of(AreaConhecimento.builder().codigo(1l).build()));
        var areaConhecimentoCurricular = executarImportacaoDocentesDadosPessoaisService.getAreaConhecimentoCurricular(NOVOS_DADOS_PESSOA, 55);
        Assert.assertNotNull(areaConhecimentoCurricular);
    }

    @Test
    public void naoDeveBuscarTipoDeficienciaEspectroAltasHabilidades() {
        var tipoDeficienciaEspectroAltasHabilidades = executarImportacaoDocentesDadosPessoaisService.getTipoDeficienciaEspectroAltasHabilidades(NOVOS_DADOS_PESSOA, professor);
        assertNotNull(tipoDeficienciaEspectroAltasHabilidades);
    }

}
