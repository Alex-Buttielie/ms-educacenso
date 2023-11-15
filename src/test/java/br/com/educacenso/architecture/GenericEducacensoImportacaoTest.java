package br.com.educacenso.architecture;

import br.com.educacenso.EducacensoApplicationTest;
import br.com.educacenso.app.constraints.TipoFiliacao;
import br.com.educacenso.app.constraints.TipoRegistro;
import br.com.educacenso.app.domains.Pessoa;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

@ExtendWith(MockitoExtension.class)
public class GenericEducacensoImportacaoTest extends EducacensoApplicationTest {

    private GenericEducacensoImportacao genericEducacensoImportacao;
    final String [] NOVOS_DADOS ={"20/08/1972", "", "TESTE", "1", "1"};
    final String [] NOVOS_DADOS_VAZIO ={};


    @BeforeEach
    public void init() {
    }

    @Test
    public void naoDeveSoltaExcecaoBuscarValorDate(){
        Assert.assertNull(genericEducacensoImportacao.stringToDate(NOVOS_DADOS_VAZIO, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToDate(null, 0));
        Assert.assertNotNull(genericEducacensoImportacao.stringToDate(NOVOS_DADOS, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToDate(NOVOS_DADOS, 1));
        Assert.assertNull(genericEducacensoImportacao.stringToDate(NOVOS_DADOS, 2));
        Assert.assertNull(genericEducacensoImportacao.stringToDate(NOVOS_DADOS, 3));
    }

    @Test
    public void deveSoltaExcecaoBuscarValorDate() {
        Assert.assertThrows(NullPointerException.class, () -> genericEducacensoImportacao.getValorDate(NOVOS_DADOS_VAZIO, 0));
        Assert.assertThrows(NullPointerException.class, () -> genericEducacensoImportacao.getValorDate(null, 0));
        Assert.assertThrows(NullPointerException.class, () -> genericEducacensoImportacao.getValorDate(NOVOS_DADOS, 1));
        Assert.assertThrows(ParseException.class, () -> genericEducacensoImportacao.getValorDate(NOVOS_DADOS, 2));
        Assert.assertThrows(ParseException.class, () -> genericEducacensoImportacao.getValorDate(NOVOS_DADOS, 3));
    }

    @Test
    public void deveBuscarString() {
        Assert.assertNull(genericEducacensoImportacao.valorString(NOVOS_DADOS_VAZIO, 0));
        Assert.assertNull(genericEducacensoImportacao.valorString(null, 0));
        Assert.assertNotNull(genericEducacensoImportacao.valorString(NOVOS_DADOS, 0));
        Assert.assertNull(genericEducacensoImportacao.valorString(NOVOS_DADOS, 1));
        Assert.assertNotNull(genericEducacensoImportacao.valorString(NOVOS_DADOS, 2));
        Assert.assertNotNull(genericEducacensoImportacao.valorString(NOVOS_DADOS, 3));
    }

    @Test
    public void deveSoltaExcecaoBuscarValorString() {
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> genericEducacensoImportacao.getValorString(NOVOS_DADOS_VAZIO, 0));
        Assert.assertThrows(NullPointerException.class, () -> genericEducacensoImportacao.getValorString(null, 0));
    }

    @Test
    public void naoDeveSoltaExcecaoBuscarValorString() {
        Assert.assertNull(genericEducacensoImportacao.getValorString(NOVOS_DADOS, 1));
        Assert.assertNotNull(genericEducacensoImportacao.getValorString(NOVOS_DADOS, 2));
        Assert.assertEquals(NOVOS_DADOS[0], genericEducacensoImportacao.getValorString(NOVOS_DADOS, 0));
    }

    @Test
    public void deveConverterStringParaBoolean() {
        Assert.assertEquals(Boolean.FALSE, genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS_VAZIO, 0));
        Assert.assertEquals(Boolean.FALSE, genericEducacensoImportacao.stringToBoolean(null, 0));
        Assert.assertEquals(Boolean.FALSE, genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS, 0));
        Assert.assertEquals(Boolean.FALSE, genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS, 1));
        Assert.assertEquals(Boolean.FALSE, genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS, 2));
        Assert.assertNotNull(genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS, 3));
        Assert.assertEquals(Boolean.TRUE, genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS, 3));
    }

    @Test
    public void deveBuscarValorInteger() {
        Assert.assertEquals(Integer.decode(NOVOS_DADOS[3]), genericEducacensoImportacao.stringToInteger(NOVOS_DADOS, 3));
        Assert.assertNull(genericEducacensoImportacao.stringToInteger(null, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToInteger(NOVOS_DADOS_VAZIO, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToInteger(NOVOS_DADOS, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToInteger(NOVOS_DADOS, 1));
        Assert.assertNull(genericEducacensoImportacao.stringToInteger(NOVOS_DADOS, 2));

    }

    @Test
    public void naoDeveConverterStringParaInteger() {
        Assert.assertNull(genericEducacensoImportacao.stringToInteger(null, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToInteger(NOVOS_DADOS_VAZIO, 0));
    }

    @Test
    public void deveSoltaExcecaoBuscarValorInteger() {
        Assert.assertThrows(NumberFormatException.class, () -> genericEducacensoImportacao.getValorInteger(null, 0));
        Assert.assertThrows(NumberFormatException.class, () -> genericEducacensoImportacao.getValorInteger(NOVOS_DADOS_VAZIO, 0));
        Assert.assertThrows(NumberFormatException.class, () -> genericEducacensoImportacao.getValorInteger(NOVOS_DADOS, 0));
        Assert.assertThrows(NumberFormatException.class, () -> genericEducacensoImportacao.getValorInteger(NOVOS_DADOS, 1));
        Assert.assertThrows(NumberFormatException.class, () -> genericEducacensoImportacao.getValorInteger(NOVOS_DADOS, 2));
    }

    @Test
    public void deveBuscarValorLong() {
        Assert.assertEquals(Integer.decode(NOVOS_DADOS[3]), genericEducacensoImportacao.stringToInteger(NOVOS_DADOS, 3));
        Assert.assertNull(genericEducacensoImportacao.stringToLong(null, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToLong(NOVOS_DADOS_VAZIO, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToLong(NOVOS_DADOS, 0));
        Assert.assertNull(genericEducacensoImportacao.stringToLong(NOVOS_DADOS, 1));
        Assert.assertNull(genericEducacensoImportacao.stringToLong(NOVOS_DADOS, 2));

    }

    @Test
    public void testeBuscaTipoRegistro() {
        var tipoRegistro = genericEducacensoImportacao.getTipoRegistro(TipoRegistro.REGISTRO_CADASTRO_ALUNO_IDENTIFICACAO.getDescricao());
        Assert.assertEquals(TipoRegistro.REGISTRO_CADASTRO_ALUNO_IDENTIFICACAO, tipoRegistro);
    }

    @Test
    public void deveTestarAtualizacaoPropriedadesObjetos() {
        var pessoa = Pessoa.builder().id(1l).build();
        var pessoaComparacao = Pessoa.builder().id(2l).build();
        genericEducacensoImportacao.atualizarPropriedadesObjeto(pessoaComparacao, pessoa);
        Assert.assertEquals(pessoaComparacao.getId(), pessoa.getId());
    }

    @Test
    public void deveTestarBuscarRegistroLido() {
        Assert.assertNull(genericEducacensoImportacao.buscaRegistroConteudoLido(null, TipoFiliacao.values()));
        Assert.assertNull(genericEducacensoImportacao.buscaRegistroConteudoLido("1", null));
        Assert.assertNull(genericEducacensoImportacao.buscaRegistroConteudoLido("10", TipoFiliacao.values()));
        Assert.assertEquals(genericEducacensoImportacao.buscaRegistroConteudoLido("1", TipoFiliacao.values()), TipoFiliacao.FILIACAO_1_OU_2);
    }

}
