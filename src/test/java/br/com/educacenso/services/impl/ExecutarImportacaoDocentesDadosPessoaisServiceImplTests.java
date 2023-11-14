package br.com.educacenso.services.impl;

import br.com.educacenso.app.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.constraints.MaiorNivelEscolaridadeConcluido;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.constraints.TipoFiliacao;
import br.com.educacenso.app.domains.AreaConhecimento;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.TipoDeficienciaEspectroAltasHabilidades;
import br.com.educacenso.repositories.AreaConhecimentoRepository;
import br.com.educacenso.repositories.AreaPosGraduacaoRepository;
import br.com.educacenso.repositories.FormacaoComplementarPedagogicaProfessorRepository;
import br.com.educacenso.repositories.OutrosCursosEspecificosRepository;
import br.com.educacenso.repositories.PessoaRepository;
import br.com.educacenso.repositories.PosGraduacaoConcluidaProfessorRepository;
import br.com.educacenso.repositories.RecursoAlunoParaAvaliacaoInepRepository;
import br.com.educacenso.repositories.TipoDeficienciaEspectroAltasHabilidadesRepository;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.services.ExecutarImportacaoDocentesDadosPessoaisService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@MockBeans({
        @MockBean(UnidadeEnsinoRepository.class),
        @MockBean(AreaConhecimentoRepository.class),
        @MockBean(AreaPosGraduacaoRepository.class),
        @MockBean(FormacaoComplementarPedagogicaProfessorRepository.class),
        @MockBean(TipoDeficienciaEspectroAltasHabilidadesRepository.class),
        @MockBean(OutrosCursosEspecificosRepository.class),
        @MockBean(RecursoAlunoParaAvaliacaoInepRepository.class),
        @MockBean(PosGraduacaoConcluidaProfessorRepository.class),
        @MockBean(PessoaRepository.class)
})
public class ExecutarImportacaoDocentesDadosPessoaisServiceImplTests {

    @TestConfiguration
    static class Configuracao {
        @Bean
        public ExecutarImportacaoDocentesDadosPessoaisService executarImportacaoDocentesDadosPessoaisService() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl();
        }
    }

    @MockBean
    private PessoaRepository pessoaRepository;
    @MockBean
    private AreaConhecimentoRepository areaConhecimentoRepository;
    @MockBean
    private ExecutarImportacaoDocentesDadosPessoaisService executarImportacaoDocentesDadosPessoaisService;
    @MockBean
    private UnidadeEnsinoRepository unidadeEnsinoRepository;
    @Mock
    private Pessoa pessoa;
    @Mock
    private AreaConhecimento areaConhecimento;

    private final String[] NOVOS_DADOS_DOCENTE = {"30","52083535","3281","","78657369168","ANGELA MARIA TEIXEIRA MARTINS",
            "20/08/1972","1","MARIA LIMA TEIXEIRA", "ANTONIO TOMAZ TEIXEIRA","2","3","2","724","5218508","0","","","","",
            "","","","","","","","","","","","","","","","","","","","","76","","1","1","6","","","","","","","","","","",
            "","","","","","","","","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"};

    @Before
    public void init() {
        when(pessoaRepository.findPessoaByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));
        when(areaConhecimentoRepository.findById(areaConhecimento.getCodigo())).thenReturn(Optional.of(areaConhecimento));
        executarImportacaoDocentesDadosPessoaisService =
                new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(pessoaRepository, unidadeEnsinoRepository, areaConhecimentoRepository,
                        null, null,
                        null, null,
                        null,null);
    }


    public void naoDeveTestarImportarLinha(){
        assertThrows(Exception.class, ()-> executarImportacaoDocentesDadosPessoaisService.importarLinhaArquivo(null));
    }



    @Test
    public void deveRastrearPessoaCacteristicasIndiv() {
        final String[] dadosPessoaStr = {"", "", "", "", "00000000000", "TESTE RASTREAMENTO POR CARACTERISTICAS"};
        final Pessoa dadosPessoa = new Pessoa().builder()
                .nome("TESTE RASTREAMENTO POR CARACTERISTICAS")
                .cpf("00000000000")
                .build();

        when(pessoaRepository.findPessoaByCpf(dadosPessoa.getCpf())).thenReturn(Optional.of(dadosPessoa));

        Pessoa pessoaRastreada = executarImportacaoDocentesDadosPessoaisService
                .rastrearPessoaCacteristicasIndiv(dadosPessoaStr).orElse(null);

        assertEquals(pessoaRastreada);
        assertEquals(pessoaRastreada.getNome(), dadosPessoa.getNome());
        assertEquals(pessoaRastreada.getCpf(), dadosPessoa.getCpf());
    }

    @Test
    public void deveRastrearPessoaCpf() {
        final String[] dadosPessoaStr = {"", "", "", "", "00000000000", "TESTE RASTREAMENTO POR CPF"};
        final Pessoa dadosPessoa = new Pessoa().builder()
                .nome("TESTE RASTREAMENTO POR CPF")
                .cpf("00000000000")
                .build();

        when(pessoaRepository.findPessoaByCpf(dadosPessoa.getCpf())).thenReturn(Optional.of(dadosPessoa));

        Optional<Pessoa> pessoaRastreada = executarImportacaoDocentesDadosPessoaisService
                .rastrearPessoaCpf(dadosPessoaStr);

        assertEquals(pessoaRastreada.isPresent(), TRUE);

    }

    @Test
    public void deveRastrearPessoaNome() {
        final String[] dadosPessoaStr = {"", "", "", "", "00000000000", "TESTE RASTREAMENTO POR CPF"};
        final Pessoa dadosPessoa = new Pessoa().builder()
                .nome("TESTE RASTREAMENTO POR CPF")
                .cpf("00000000000")
                .build();

        when(pessoaRepository.findPessoaByNome(dadosPessoa.getNome())).thenReturn(Optional.of(dadosPessoa));

        Optional<Pessoa> pessoaRastreada = executarImportacaoDocentesDadosPessoaisService
                .rastrearPessoaNome(dadosPessoaStr);

        assertEquals(pessoaRastreada.isPresent(), TRUE);

    }



    @Test
    public void deveBuscarLocalizacaoDiferenciadaResidencia() {
        final int POSICAO_CAMPO_LOCALIZACAO = 42;
        LocalizacaoDiferenciadaResidencia localizacao = executarImportacaoDocentesDadosPessoaisService
                .getLocalizacaoDiferenciadaResidencia(NOVOS_DADOS_DOCENTE[POSICAO_CAMPO_LOCALIZACAO]);
        assertNotNull(localizacao);
    }

    @Test
    public void deveBuscarNacionalidadeCorreta() {
        final String CODIGO_NACIONALIDADE = Nacionalidade.BRASILEIRA.getCodigoEducacenso();
        Nacionalidade nacionalidade = executarImportacaoDocentesDadosPessoaisService.getNacionalidade(CODIGO_NACIONALIDADE);
        assertEquals(nacionalidade, Nacionalidade.BRASILEIRA);
    }

    @Test
    public void deveBuscarMaiorNivelEscolaridadeConcluida() {
        final String CODIGO_NIVEL = MaiorNivelEscolaridadeConcluido.ENSINO_MEDIO.getDescricao();
        MaiorNivelEscolaridadeConcluido nivel = executarImportacaoDocentesDadosPessoaisService.getMaiorNivelEscolaridadeConcluida(CODIGO_NIVEL);
        assertEquals(nivel, MaiorNivelEscolaridadeConcluido.ENSINO_MEDIO);
    }

    /*@Test
    public void deveBuscarRecursoAlunoParaAvaliacaoInep() {
        RecursoAlunoParaAvaliacaoInep recurso = RecursoAlunoParaAvaliacaoInep.builder().auxilioLedor(true).build();

        Pessoa pessoaComId = Pessoa.builder()
                .id(1l)
                .recursoAlunoParaAvaliacaoInep(recurso)
                .build();

        Pessoa pessoaSemId = Pessoa.builder()
                .recursoAlunoParaAvaliacaoInep(recurso)
                .build();

        assertEquals(pessoaSemId.getRecursoAlunoParaAvaliacaoInep(), recurso);
        assertNull(pessoaSemId.getId());
        assertEquals(pessoaComId.getRecursoAlunoParaAvaliacaoInep(), recurso);
        assertNotNull(pessoaComId.getId());

    }*/

    @Test
    public void deveBuscarTipoEnsinoMedioCursado() {
        final String CODIGO_ENSINO_MEDIO_CURSADO = TipoEnsinoMedioCursado.MODALIDADE_NORMAL_MAGISTERIO.getCodigoEducacenso();
        TipoEnsinoMedioCursado ensinoMedioCursado = executarImportacaoDocentesDadosPessoaisService.getTipoEnsinoMedioCursado(CODIGO_ENSINO_MEDIO_CURSADO);
        assertEquals(ensinoMedioCursado, TipoEnsinoMedioCursado.MODALIDADE_NORMAL_MAGISTERIO);
    }

    @Test
    public void deveBuscarTipoFiliacao() {
        TipoFiliacao tipoFiliacao = executarImportacaoDocentesDadosPessoaisService.getTipoFiliacao(NOVOS_DADOS_DOCENTE[7]);
        assertEquals(tipoFiliacao, TipoFiliacao.FILIACAO_1_OU_2);
    }

    @Test
    public void naoDeveBuscarTipoDeficienciaEspectroAltasHabilidades() {
        TipoDeficienciaEspectroAltasHabilidades tipoDeficienciaEspectroAltasHabilidades = executarImportacaoDocentesDadosPessoaisService.getTipoDeficienciaEspectroAltasHabilidades(NOVOS_DADOS_DOCENTE, Optional.ofNullable(pessoa));
        assertNotNull(tipoDeficienciaEspectroAltasHabilidades);
    }

    @Test
    public void deveBuscarDadosPessoaNaLinha() {
        Pessoa pessoaConsultada =
                executarImportacaoDocentesDadosPessoaisService.getDadosPessoaNaLinha(NOVOS_DADOS_DOCENTE, Optional.ofNullable(pessoa));
        assertNotNull(pessoaConsultada);
    }

}
