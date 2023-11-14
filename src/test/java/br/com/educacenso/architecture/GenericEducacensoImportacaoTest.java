package br.com.educacenso.architecture;

import br.com.educacenso.EducacensoApplicationTest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GenericEducacensoImportacaoTest extends EducacensoApplicationTest {

    private GenericEducacensoImportacao genericEducacensoImportacao;


    @BeforeEach
    public void init() {
    }

    @Test
    public void deveConverterSimNaoNumeralStrParaBoolean() {
        var retorno =  this.genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS_PESSOA, 7);
        Assert.assertEquals(Boolean.TRUE,retorno);
    }

}
