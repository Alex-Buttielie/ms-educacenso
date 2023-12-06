package br.com.educacenso.app.services.impl;

import br.com.educacenso.app.constraints.TipoMediacao;
import br.com.educacenso.app.domains.Turma;
import br.com.educacenso.app.repositories.TurmaRepository;
import br.com.educacenso.app.repositories.UnidadeEnsinoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExecutarImportacaoTurmasServiceImplTest {

    @InjectMocks
    private ExecutarImportacaoTurmasServiceImpl executarImportacaoTurmasService;
    @Mock
    private UnidadeEnsinoRepository unidadeEnsinoRepository;
    @Mock
    private TurmaRepository turmaRepository;
    private final String [] LINHA = {"20","52083535","293","","BERCARIO","1","07","00","17","00","0","1","1","1","1","1","0","1","0","0","","","","","","","0","1","1","","","","","","","","","","","","","","","","","","","","","","","","","","",""};

    @BeforeEach
    public void init() {

    }

    @Test
    public void deveTestarImportacaoDaLinha() {
        var turmaMock = Turma.builder().id(1l).build();
        when(turmaRepository.findTurmaByCodigoTurma(any())).thenReturn(turmaMock);
        when(turmaRepository.saveAndFlush(any())).thenReturn(turmaMock);
        var retorno = executarImportacaoTurmasService.importarLinhaArquivo(LINHA);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(turmaMock.getId(), retorno.get().getId());
    }

    @Test
    public void naoDeveTestarImportacaoDaLinha() {
        when(turmaRepository.findTurmaByCodigoTurma(any())).thenReturn(null);
        var retorno = executarImportacaoTurmasService.importarLinhaArquivo(null);
        Assert.assertTrue(retorno.isEmpty());
    }

    @Test
    public void deveBuscarTipoMediacao() {
        var tipoMediacao = executarImportacaoTurmasService.buscarMediacao("3");
        Assert.assertEquals(TipoMediacao.EDUCACAO_DISTANCIA, tipoMediacao);
    }


    @Test
    void deveTestarBuscarDeHorarioDeFuncionamentoDaTurma() {
        var turma = Optional.of(Turma.builder().build());
        var horarioFuncionamento = executarImportacaoTurmasService.buscarHorarioFuncionamentoTurma(LINHA, turma);
        Assert.assertNotNull(horarioFuncionamento);
        Assert.assertEquals("07", horarioFuncionamento.getHoraInicial());
        Assert.assertEquals("17", horarioFuncionamento.getHoraFinal());
        Assert.assertEquals("00", horarioFuncionamento.getMinutoInicial());
        Assert.assertEquals("00", horarioFuncionamento.getMinutoFinal());
    }

    @Test
    public void deveTestarBuscaDiasSemanaDaTurma() {
        var turma = Optional.of(Turma.builder().build());
        var diasSemana = executarImportacaoTurmasService.buscarDiasSemanaDaTurma(LINHA, turma);
        Assert.assertNotNull(diasSemana);
        Assert.assertEquals(Boolean.FALSE,diasSemana.getDomingo());
        Assert.assertEquals(Boolean.TRUE,diasSemana.getSegunda());
        Assert.assertEquals(Boolean.TRUE,diasSemana.getTerca());
        Assert.assertEquals(Boolean.TRUE,diasSemana.getQuarta());
        Assert.assertEquals(Boolean.TRUE,diasSemana.getQuinta());
        Assert.assertEquals(Boolean.TRUE,diasSemana.getSexta());
        Assert.assertEquals(Boolean.FALSE,diasSemana.getSabado());
    }

}
