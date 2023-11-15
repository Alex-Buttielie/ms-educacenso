package br.com.educacenso.services.impl;

import br.com.educacenso.app.GenericPessoaImportacaoTest;
import br.com.educacenso.app.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.constraints.MaiorNivelEscolaridadeConcluido;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.constraints.TipoFiliacao;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.Professor;
import br.com.educacenso.app.domains.RecursoAlunoParaAvaliacaoInep;
import br.com.educacenso.repositories.AreaConhecimentoRepository;
import br.com.educacenso.repositories.AreaPosGraduacaoRepository;
import br.com.educacenso.repositories.PessoaRepository;
import br.com.educacenso.repositories.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExecutarImportacaoDocentesDadosPessoaisServiceImplTest extends GenericPessoaImportacaoTest {

    @InjectMocks
    private ExecutarImportacaoDocentesDadosPessoaisServiceImpl executarImportacaoDocentesDadosPessoaisService;
    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private AreaConhecimentoRepository areaConhecimentoRepository;
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

        executarImportacaoDocentesDadosPessoaisService = new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(pessoaRepository, unidadeEnsinoRepository, areaConhecimentoRepository,
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
    public void deveBuscarMaiorNivelEscolaridadeConcluida() {
        final var CODIGO_NIVEL = MaiorNivelEscolaridadeConcluido.ENSINO_MEDIO.getDescricao();
        MaiorNivelEscolaridadeConcluido nivel = executarImportacaoDocentesDadosPessoaisService.getMaiorNivelEscolaridadeConcluida(CODIGO_NIVEL);
        assertEquals( MaiorNivelEscolaridadeConcluido.ENSINO_MEDIO, nivel);
    }

    @Test
    public void deveBuscarTipoEnsinoMedioCursado() {
        final var CODIGO_ENSINO_MEDIO_CURSADO = String.valueOf(TipoEnsinoMedioCursado.CURSO_TECNICO.getCodigoEducacenso());
        TipoEnsinoMedioCursado ensinoMedioCursado = executarImportacaoDocentesDadosPessoaisService.getTipoEnsinoMedioCursado(CODIGO_ENSINO_MEDIO_CURSADO);
        assertEquals(TipoEnsinoMedioCursado.CURSO_TECNICO, ensinoMedioCursado);
    }

    @Test
    public void deveRastrearPessoaCacteristicasIndiv() {
        when(pessoaRepository.findPessoaByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));
        var pessoaRastreada = executarImportacaoDocentesDadosPessoaisService.rastrearPessoaCacteristicasIndiv(NOVOS_DADOS_PESSOA).orElse(null);
        assertNotNull(pessoaRastreada);
        assertEquals(pessoaRastreada.getNome(), pessoa.getNome());
        assertEquals(pessoaRastreada.getCpf(), pessoa.getCpf());
    }

    @Test
    public void deveRastrearPessoaCpf() {
        when(pessoaRepository.findPessoaByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));
        var pessoaRastreada = executarImportacaoDocentesDadosPessoaisService.rastrearPessoaCpf(NOVOS_DADOS_PESSOA);
        assertEquals(TRUE, pessoaRastreada.isPresent());
    }

    @Test
    public void deveRastrearPessoaNome() {
        when(pessoaRepository.findPessoaByNome(pessoa.getNome())).thenReturn(Optional.of(pessoa));
        var pessoaRastreada = executarImportacaoDocentesDadosPessoaisService.rastrearPessoaNome(NOVOS_DADOS_PESSOA);
        assertEquals(TRUE, pessoaRastreada.isPresent());

    }

    @Test
    public void deveBuscarLocalizacaoDiferenciadaResidencia() {
        var localizacaoDiferenciadaResidencia = String.valueOf(LocalizacaoDiferenciadaResidencia.AREA_ASSENTAMENTO.ordinal());
        var localizacao = executarImportacaoDocentesDadosPessoaisService.getLocalizacaoDiferenciadaResidencia(localizacaoDiferenciadaResidencia);
        assertEquals(String.valueOf(localizacao.ordinal()), localizacaoDiferenciadaResidencia);
    }

    @Test
    public void deveBuscarNacionalidadeCorreta() {
        final var CODIGO_NACIONALIDADE = String.valueOf(Nacionalidade.BRASILEIRA.ordinal());
        Nacionalidade nacionalidade = executarImportacaoDocentesDadosPessoaisService.getNacionalidade(CODIGO_NACIONALIDADE);
        assertEquals( Nacionalidade.BRASILEIRA, nacionalidade);
    }

    @Test
    public void deveBuscarRecursoAlunoParaAvaliacaoInep() {
        RecursoAlunoParaAvaliacaoInep recurso = RecursoAlunoParaAvaliacaoInep.builder().auxilioLedor(true).build();

        var professor = Professor.builder()
                .id(1l)
                .recursoAlunoParaAvaliacaoInep(recurso)
                .build();

        var professoSemId = Professor.builder()
                .recursoAlunoParaAvaliacaoInep(recurso)
                .build();

        assertEquals(professoSemId.getRecursoAlunoParaAvaliacaoInep(), recurso);
        assertNull(professoSemId.getId());
        assertEquals(professor.getRecursoAlunoParaAvaliacaoInep(), recurso);
        assertNotNull(professor.getId());

    }

    @Test
    public void deveBuscarTipoFiliacao() {
        var tipoFiliacao = executarImportacaoDocentesDadosPessoaisService.getTipoFiliacao(String.valueOf(TipoFiliacao.FILIACAO_1_OU_2.ordinal()));
        assertEquals( TipoFiliacao.FILIACAO_1_OU_2, tipoFiliacao);
    }

    @Test
    public void naoDeveBuscarTipoDeficienciaEspectroAltasHabilidades() {
        var tipoDeficienciaEspectroAltasHabilidades = executarImportacaoDocentesDadosPessoaisService.getTipoDeficienciaEspectroAltasHabilidades(NOVOS_DADOS_PESSOA, professor);
        assertNotNull(tipoDeficienciaEspectroAltasHabilidades);
    }

    @Test
    public void deveBuscarDadosPessoaNaLinha() {
        var pessoaConsultada = executarImportacaoDocentesDadosPessoaisService.getDadosPessoaNaLinha(NOVOS_DADOS_PESSOA, Optional.ofNullable(pessoa));
        assertNotNull(pessoaConsultada);
    }

}
