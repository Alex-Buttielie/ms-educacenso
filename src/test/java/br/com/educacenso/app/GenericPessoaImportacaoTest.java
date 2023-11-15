package br.com.educacenso.app;

import br.com.educacenso.EducacensoApplicationTest;
import br.com.educacenso.app.constraints.CorRaca;
import br.com.educacenso.app.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.constraints.Paises;
import br.com.educacenso.app.constraints.TipoRegistro;
import br.com.educacenso.app.repositories.PessoaRepository;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class GenericPessoaImportacaoTest extends EducacensoApplicationTest {

    @InjectMocks
    private GenericPessoaImportacao genericPessoaImportacaoService;
    @Mock
    protected PessoaRepository pessoaRepository;
    @Mock
    protected UnidadeEnsinoRepository unidadeEnsinoRepository;
    @Mock
    protected Pessoa pessoa;

    @BeforeEach
    public void init() {
    }

    @Test
    public void devebuscarCorRaca() {
        CorRaca corRaca = CorRaca.PARDA;
        Assert.assertNotNull(corRaca);
    }

    @Test
    public void deveAtualizarDadosPessoa() {
        var pessoaAtualizada = genericPessoaImportacaoService.atualizarDadosPessoa(NOVOS_DADOS_PESSOA);
        assertNotNull(pessoaAtualizada);
        assertNotNull(pessoaAtualizada.getTipoRegistro());
        assertEquals(TipoRegistro.REGISTRO_CADASTRO_DOCENTE_IDENTIFICACAO, pessoaAtualizada.getTipoRegistro());
        assertEquals(Paises.ESPANHA.getValor(), pessoaAtualizada.getPaisNacionalidade().getValor());
        assertEquals(Nacionalidade.EXTRANGEIIRO, pessoaAtualizada.getNacionalidade());
    }

    @Test
    public void deveAtualizarDadosPessoaConsultada() {
        var pessoaConsulta = genericPessoaImportacaoService.atualizarDadosPessoaConsultada(Optional.of(pessoa), NOVOS_DADOS_PESSOA);
        assertNotNull(pessoaConsulta);
        assertNotNull(pessoaConsulta.getId());
        assertEquals(pessoaConsulta.getId(), pessoa.getId());
    }

    @Test
    public void deveAtualizarDadosPessoaNaoConsultada() {
        var pessoaConsulta = genericPessoaImportacaoService.atualizarDadosPessoaNaoConsultada(DADOS_PESSOA_VAZIO_STR);
        var pessoaConsultaDadosPreenchidos = genericPessoaImportacaoService.atualizarDadosPessoaNaoConsultada(NOVOS_DADOS_PESSOA);
        assertNotNull(pessoaConsulta);
        assertNotNull(pessoaConsultaDadosPreenchidos);
    }

    @Test
    public void deveBuscarLocalizacaoZonaResidencia() {
        var localizacaoStr = String.valueOf(LocalizacaoZonaResidencia.RURAL.ordinal());
        LocalizacaoZonaResidencia localizacao = genericPessoaImportacaoService.getLocalizacaoZonaResidencia(localizacaoStr);
        assertEquals(String.valueOf(localizacao.ordinal()), localizacaoStr);

    }

    @Test
    public void naoDeveBuscarLocalizacaoZonaResidencia() {
        var localizacaoDiferenciadaResidencia = genericPessoaImportacaoService.getLocalizacaoDiferenciadaResidencia(null);
        assertNull(localizacaoDiferenciadaResidencia);
        localizacaoDiferenciadaResidencia = genericPessoaImportacaoService.getLocalizacaoDiferenciadaResidencia("");
        assertNull(localizacaoDiferenciadaResidencia);
    }

}
