package br.com.educacenso.app;

import br.com.educacenso.EducacensoApplicationTest;
import br.com.educacenso.app.constraints.CorRaca;
import br.com.educacenso.app.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.constraints.Nacionalidade;
import br.com.educacenso.app.constraints.Paises;
import br.com.educacenso.app.constraints.Sexo;
import br.com.educacenso.app.constraints.TipoFiliacao;
import br.com.educacenso.app.constraints.TipoRegistro;
import br.com.educacenso.app.domains.Pessoa;
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

import static br.com.educacenso.architecture.GenericEducacensoImportacao.stringToInteger;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenericPessoaImportacaoTest extends EducacensoApplicationTest {

    @InjectMocks
    private GenericPessoaImportacao genericPessoaImportacao;
    @Mock
    protected PessoaRepository pessoaRepository;
    @Mock
    protected UnidadeEnsinoRepository unidadeEnsinoRepository;
    @Mock
    protected Pessoa pessoa;

    @BeforeEach
    public void init() {
        pessoa = new Pessoa().builder()
                .id(1l)
                .nome("ANGELA MARIA TEIXEIRA MARTINS")
                .cpf("78657369168")
                .enderecoEletronicoEmail("campo93naoesquecer@gmail.com")
                .build();
    }

    @Test
    public void deveBuscarCorRaca() {
        var corRaca = genericPessoaImportacao.getCorRaca(NOVOS_DADOS_PESSOA, 11);
        Assert.assertEquals( CorRaca.PARDA, corRaca);
    }

    @Test
    public void deveBuscarSexo() {
        var sexo = genericPessoaImportacao.getSexo(stringToInteger(NOVOS_DADOS_PESSOA, 10));
        Assert.assertEquals(Sexo.FEMININO, sexo);
    }

    @Test
    public void deveBuscarPais() {
        var pais = genericPessoaImportacao.getPais(stringToInteger(NOVOS_DADOS_PESSOA, 13));
        Assert.assertEquals(Paises.ESPANHA, pais);
    }

    @Test
    public void deveBuscarPaisFunction() {
        var pais = Optional.ofNullable(stringToInteger(NOVOS_DADOS_PESSOA, 13))
                .map(genericPessoaImportacao.getFunctionPaisPorDescricao())
                .orElse(null);
        Assert.assertNotNull(pais);
        Assert.assertEquals(Paises.ESPANHA, pais);
    }

    @Test
    public void deveBuscarSexoFunction() {
        var sexo = Optional.ofNullable(stringToInteger(NOVOS_DADOS_PESSOA, 10))
                .map(genericPessoaImportacao.getFunctionSexo())
                .orElse(null);
        Assert.assertNotNull(sexo);
        Assert.assertEquals(Sexo.FEMININO, sexo);
    }

    @Test
    public void deveSalvarPessoa() {
        when(pessoaRepository.save(any())).thenReturn(Pessoa.builder().id(1l).build());
        var retorno = genericPessoaImportacao.salvarPessoa(this.pessoa);
        assertNotNull(retorno);
        assertEquals( 1l, retorno.getId());
    }

    @Test
    public void deveAtualizarDadosPessoa() {
        var pessoaAtualizada = genericPessoaImportacao.atualizarDadosPessoa(NOVOS_DADOS_PESSOA);
        assertNotNull(pessoaAtualizada);
        assertNotNull(pessoaAtualizada.getTipoRegistro());
        assertEquals(TipoRegistro.REGISTRO_CADASTRO_DOCENTE_IDENTIFICACAO, pessoaAtualizada.getTipoRegistro());
        assertEquals(Paises.ESPANHA.getValor(), pessoaAtualizada.getPaisNacionalidade().getValor());
        assertEquals(Nacionalidade.BRASILEIRA_NASCIDO_EXTERIOR_OU_NATURALIZADO, pessoaAtualizada.getNacionalidade());
    }

    @Test
    public void deveRastrearPessoaCacteristicasIndiv() {
        when(pessoaRepository.findPessoaByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));
        var pessoaRastreada = genericPessoaImportacao.rastrearPessoaCacteristicasIndiv(NOVOS_DADOS_PESSOA).orElse(null);
        assertNotNull(pessoaRastreada);
        assertEquals(pessoaRastreada.getNome(), pessoa.getNome());
        assertEquals(pessoaRastreada.getCpf(), pessoa.getCpf());
    }

    @Test
    public void deveRastrearPessoaCpf() {
        when(pessoaRepository.findPessoaByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));
        var pessoaRastreada = genericPessoaImportacao.rastrearPessoaCpf(NOVOS_DADOS_PESSOA);
        assertEquals(TRUE, pessoaRastreada.isPresent());
    }

    @Test
    public void deveRastrearPessoaNome() {
        when(pessoaRepository.findPessoaByNome(pessoa.getNome())).thenReturn(Optional.of(pessoa));
        var pessoaRastreada = genericPessoaImportacao.rastrearPessoaNome(NOVOS_DADOS_PESSOA);
        assertEquals(TRUE, pessoaRastreada.isPresent());

    }

    @Test
    public void deveAtualizarDadosPessoaConsultada() {
        var pessoaConsulta = genericPessoaImportacao.atualizarDadosPessoaConsultada(Optional.of(pessoa), NOVOS_DADOS_PESSOA);
        assertNotNull(pessoaConsulta);
        assertNotNull(pessoaConsulta.getId());
        assertEquals(pessoaConsulta.getId(), pessoa.getId());
    }

    @Test
    public void deveAtualizarDadosPessoaNaoConsultada() {
        var pessoaConsulta = genericPessoaImportacao.atualizarDadosPessoaNaoConsultada(DADOS_PESSOA_VAZIO_STR);
        var pessoaConsultaDadosPreenchidos = genericPessoaImportacao.atualizarDadosPessoaNaoConsultada(NOVOS_DADOS_PESSOA);
        assertNotNull(pessoaConsulta);
        assertNotNull(pessoaConsultaDadosPreenchidos);
    }

    @Test
    public void deveBuscarDadosPessoaNaLinha() {
        var pessoaConsultada = genericPessoaImportacao.getDadosPessoaNaLinha(NOVOS_DADOS_PESSOA, Optional.ofNullable(pessoa));
        assertNotNull(pessoaConsultada);
    }

    @Test
    public void deveBuscarLocalizacaoZonaResidencia() {
        var localizacaoZonaResidencia = LocalizacaoZonaResidencia.RURAL;
        LocalizacaoZonaResidencia localizacao = genericPessoaImportacao.getLocalizacaoZonaResidencia(localizacaoZonaResidencia.getValor());
        assertEquals(localizacao, localizacao);

    }

    @Test
    public void deveBuscarLocalizacaoDiferenciadaResidencia() {
        var localizacaoDiferenciadaResidencia = LocalizacaoDiferenciadaResidencia.AREA_ASSENTAMENTO;
        var localizacao = genericPessoaImportacao.getLocalizacaoDiferenciadaResidencia(localizacaoDiferenciadaResidencia.getValor());
        assertEquals(localizacao, localizacaoDiferenciadaResidencia);
    }

    @Test
    public void naoDeveBuscarLocalizacaoZonaResidencia() {
        var localizacaoDiferenciadaResidencia = genericPessoaImportacao.getLocalizacaoDiferenciadaResidencia(null);
        assertNull(localizacaoDiferenciadaResidencia);
        localizacaoDiferenciadaResidencia = genericPessoaImportacao.getLocalizacaoDiferenciadaResidencia("");
        assertNull(localizacaoDiferenciadaResidencia);
    }

    @Test
    public void deveBuscarNacionalidadeCorreta() {
        final var CODIGO_NACIONALIDADE = Nacionalidade.BRASILEIRA.getValorStr();
        Nacionalidade nacionalidade = genericPessoaImportacao.getNacionalidade(CODIGO_NACIONALIDADE);
        assertEquals( Nacionalidade.BRASILEIRA, nacionalidade);
    }

    @Test
    public void deveBuscarTipoFiliacao() {
        var filiacaoStr = TipoFiliacao.FILIACAO_1_OU_2;
        var tipoFiliacao = genericPessoaImportacao.getTipoFiliacao(filiacaoStr.getValor());
        assertEquals(TipoFiliacao.FILIACAO_1_OU_2, tipoFiliacao);
    }

}
