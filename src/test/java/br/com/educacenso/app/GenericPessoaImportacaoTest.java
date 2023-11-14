package br.com.educacenso.app;

import br.com.educacenso.app.constraints.CorRaca;
import br.com.educacenso.app.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.UnidadeEnsino;
import br.com.educacenso.contraints.Paises;
import br.com.educacenso.contraints.TipoRegistro;
import br.com.educacenso.repositories.PessoaRepository;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenericPessoaImportacaoTest {

    @InjectMocks
    private GenericPessoaImportacao genericPessoaImportacaoService;
    @Mock
    protected PessoaRepository pessoaRepository;
    @Mock
    protected UnidadeEnsinoRepository unidadeEnsinoRepository;
    @Mock
    protected Pessoa pessoaParaSalvar;

    private final String[] NOVOS_DADOS_DOCENTE = {"30","52083535","3281","","78657369168","ANGELA MARIA TEIXEIRA MARTINS",
            "20/08/1972","1","MARIA LIMA TEIXEIRA", "ANTONIO TOMAZ TEIXEIRA","2","3","2","724","5218508","0","","","","",
            "","","","","","","","","","","","","","","","","","","","","76","","1","1","6","","","","","","","","","","",
            "","","","","","","","","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"};

    private final String[] DADOS_PESSOA_VAZIO_STR = {};

    @Before
    public void setUp() {
        pessoaParaSalvar = new Pessoa();
    }


    @Test
    public void devebuscarCorRaca() {
        CorRaca corRaca = CorRaca.PARDA;
        Assert.assertNotNull(corRaca);
    }

    @Before
    public void init() {
        genericPessoaImportacaoService = new GenericPessoaImportacao(pessoaRepository, unidadeEnsinoRepository);
    }

    @Test
    public void deveAtualizarDadosPessoa() {
        Pessoa pessoaAtualizada = genericPessoaImportacaoService.atualizarDadosPessoa(NOVOS_DADOS_DOCENTE);

        assertNotNull(pessoaAtualizada);
        /** vai para o teste getDadosPessoaNaLinha**/
        assertNotNull(pessoaAtualizada.getTipoRegistro());
        assertEquals(TipoRegistro.REGISTRO_CADASTRO_DOCENTE_IDENTIFICACAO, pessoaAtualizada.getTipoRegistro());
        assertEquals(Paises.ESPANHA.getValor(), pessoaAtualizada.getPaisNacionalidade().getValor());
        assertEquals(Nacionalidade.BRASILEIRA_NASCIDO_EXTERIOR_OU_NATURALIZADO, pessoaAtualizada.getNacionalidade());

    }

    @Test
    public void deveAtualizarDadosPessoaConsultada() {
        final String[] dadosPessoaStr = {"1"};
        final Pessoa dadosPessoa = new Pessoa().builder().id(1l).build();

        Pessoa pessoaConsulta = genericPessoaImportacaoService.atualizarDadosPessoaConsultada(Optional.of(dadosPessoa), dadosPessoaStr);

        assertNotNull(pessoaConsulta);
        assertNotNull(pessoaConsulta.getId());

    }

    @Test
    public void deveAtualizarDadosPessoaNaoConsultada() {
        Pessoa pessoaConsulta = genericPessoaImportacaoService.atualizarDadosPessoaNaoConsultada(DADOS_PESSOA_VAZIO_STR);
        assertNotNull(pessoaConsulta);

    }

    @Test
    public void deveBuscarLocalizacaoZonaResidencia() {
        final int POSICAO_CAMPO_LOCALIZACAO = 42;
        LocalizacaoZonaResidencia localizacao = genericPessoaImportacaoService
                .getLocalizacaoZonaResidencia(NOVOS_DADOS_DOCENTE[POSICAO_CAMPO_LOCALIZACAO]);
        assertNotNull(localizacao);

    }

    @Test
    public void naoDeveBuscarLocalizacaoZonaResidencia() {
        when(unidadeEnsinoRepository.findById(any())).thenReturn(Optional.of(UnidadeEnsino.builder().codigoInep(52104346l).build()));
        Pessoa pessoaConsulta = genericPessoaImportacaoService.atualizarDadosPessoaNaoConsultada(DADOS_PESSOA_VAZIO_STR);
        assertNull(pessoaConsulta.getLocalizacaoZonaResidencia());

    }

}
