package br.com.educacenso.architecture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GenericEducacensoImportacaoTest {

    private GenericEducacensoImportacao genericEducacensoImportacao;

    private final String[] NOVOS_DADOS_DOCENTE = {"30","52083535","3281","","78657369168","ANGELA MARIA TEIXEIRA MARTINS",
            "20/08/1972","1","MARIA LIMA TEIXEIRA", "ANTONIO TOMAZ TEIXEIRA","2","3","2","724","5218508","0","","","","",
            "","","","","","","","","","","","","","","","","","","","","76","","1","1","6","","","","","","","","","","",
            "","","","","","","","","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"};

    @Before
    public void init() {
    }

    @Test
    public void deveConverterSimNaoNumeralStrParaBoolean() {
        var retorno =  this.genericEducacensoImportacao.stringToBoolean(NOVOS_DADOS_DOCENTE, 7);
        Assert.assertEquals(retorno, true);
    }

}
