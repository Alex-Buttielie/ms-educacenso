package br.com.educacenso.app.services.impl;

import br.com.educacenso.app.constraints.FormaOcupacaoPredio;
import br.com.educacenso.app.domains.AbastecimentoAgua;
import br.com.educacenso.app.domains.AcessoInternet;
import br.com.educacenso.app.domains.DependenciasFisicas;
import br.com.educacenso.app.domains.DestinacaoLixo;
import br.com.educacenso.app.domains.EquipamentosExistentesUnidade;
import br.com.educacenso.app.domains.EquipamentosUsadosAlunosAcessoInternet;
import br.com.educacenso.app.domains.EscolasComQualCompartilha;
import br.com.educacenso.app.domains.EsgotamentoSanitario;
import br.com.educacenso.app.domains.FonteEnergiaEletrica;
import br.com.educacenso.app.domains.IdiomaEnsino;
import br.com.educacenso.app.domains.InstrumentosMateriaisSocioCulturais;
import br.com.educacenso.app.domains.LinguaIndigena;
import br.com.educacenso.app.domains.LocalFuncionamentoEscola;
import br.com.educacenso.app.domains.OrgaosColegiadosFuncionamentoEscola;
import br.com.educacenso.app.domains.QuantidadeComputadoresEmUsoAlunos;
import br.com.educacenso.app.domains.QuantidadeEquipamentosProcessoAprendizagem;
import br.com.educacenso.app.domains.RecursoPessoasDeficientes;
import br.com.educacenso.app.domains.RedeLocalInterligacaoComputadores;
import br.com.educacenso.app.domains.ReservaVagasSistemaCotas;
import br.com.educacenso.app.domains.TotalProfissionaisAtivosEscola;
import br.com.educacenso.app.domains.TratamentoLixo;
import br.com.educacenso.app.domains.UnidadeEnsino;
import br.com.educacenso.app.services.ExecutarImportacaoUnidadesEnsinoInfraestruturaService;
import br.com.educacenso.app.repositories.LinguaIndigenaRepository;
import br.com.educacenso.app.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.architecture.GenericEducacensoImportacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Qualifier("unidadeEnsinoInfraestrutura")
@Component
public class ExecutarImportacaoUnidadesEnsinoInfraestruturaServiceImpl
        extends GenericEducacensoImportacao
        implements ExecutarImportacaoUnidadesEnsinoInfraestruturaService {

    @Autowired
    @Qualifier("unidadeEnsinoRepository")
    private UnidadeEnsinoRepository unidadeEnsinoRepository;
    @Autowired
    @Qualifier("linguaIndigenaRepository")
    private LinguaIndigenaRepository linguaIndigenaRepository;

    public ExecutarImportacaoUnidadesEnsinoInfraestruturaServiceImpl(UnidadeEnsinoRepository unidadeEnsinoRepository,
                                                                     LinguaIndigenaRepository linguaIndigenaRepository) {
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
        this.linguaIndigenaRepository = linguaIndigenaRepository;
    }


    @Override
    public Optional<UnidadeEnsino> importarLinhaArquivo(String[] conteudoLinha) {
        return Optional.of(salvarDadosUnidadeEnsino(atualizarDadosUnidadeEnsino(conteudoLinha)));
    }

    private UnidadeEnsino getDadosDaUnidadeEnsinoNaLinha(String[] conteudoLinha, Optional<UnidadeEnsino> unidadeOptional) {
        return  UnidadeEnsino
                .builder()
                .tipoDeRegistro(getTipoRegistro(valorString(conteudoLinha, 0)))
                .codigoInep(stringToLong(conteudoLinha, 1))
                .localFuncionamentoEscola(getLocalFuncionamentoEscola(conteudoLinha, unidadeOptional))
                .escolasComQualCompartilha(getEscolaComQualCompartilha(conteudoLinha, unidadeOptional))
                .abastecimentoAgua(getAbastecimentoAgua(conteudoLinha, unidadeOptional))
                .fonteEnergiaEletrica(getFonteEnergiaEletrica(conteudoLinha, unidadeOptional))
                .esgotamentoSanitario(getEsgotamentoSanitario(conteudoLinha, unidadeOptional))
                .destinacaoLixo(getDestinacaoLixo(conteudoLinha, unidadeOptional))
                .tratamentoLixo(getTratamentoLixo(conteudoLinha, unidadeOptional))
                .dependenciasFisicas(getDependenciasFisicas(conteudoLinha, unidadeOptional))
                .recursoPessoasDeficientes(getRecursosPessoasDeficientes(conteudoLinha, unidadeOptional))
                .equipamentosExistentesUnidade(getEquipamentosExistentes(conteudoLinha, unidadeOptional))
                .quantidadeEquipamentosProcessoAprendizagem(getQuantidadeEquipamentosUsadosProcessoAprendizagem(conteudoLinha, unidadeOptional))
                .quantidadeComputadoresEmUsoAlunos(getQuantidadeComputadoresEmUsoPeloAlunos(conteudoLinha, unidadeOptional))
                .acessoInternet(getAcessoInternet(conteudoLinha, unidadeOptional))
                .equipamentosUsadosAlunosAcessoInternet(getEquipamentosUsadosAlunosAcessosInternet(conteudoLinha, unidadeOptional))
                .redeLocalInterligacaoComputadores(getRedeLocalInterligacaoComputadores(conteudoLinha, unidadeOptional))
                .totalProfissionaisAtivosEscola(getTotalProfissionaisAtivosEscola(conteudoLinha, unidadeOptional))
                .instrumentosMateriaisSocioCulturais(getInstrumentosMateriaisSocioCulturais(conteudoLinha, unidadeOptional))
                .idiomaEnsino(getIdiomaEnsino(conteudoLinha, unidadeOptional))
                .reservaVagasSistemaCotas(getReservaVagasSistemaCotas(conteudoLinha, unidadeOptional))
                .orgaosColegiadosFuncionamentoEscola(getOrgaosColegiadosFuncionamentoEscola(conteudoLinha, unidadeOptional))
                .build();

    }

    private OrgaosColegiadosFuncionamentoEscola getOrgaosColegiadosFuncionamentoEscola(String[] conteudoLinha,
                                                                                       Optional<UnidadeEnsino> unidadeOptional) {
        try {

            var orgaosColegiadosFuncionamentoEscola = unidadeOptional
                    .map(UnidadeEnsino::getOrgaosColegiadosFuncionamentoEscola)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  OrgaosColegiadosFuncionamentoEscola
                    .builder()
                    .id(orgaosColegiadosFuncionamentoEscola.map(OrgaosColegiadosFuncionamentoEscola::getId).orElse(null))
                    .associacaoPais(stringToBoolean(conteudoLinha, 161))
                    .associacaoDePaisEMestres(stringToBoolean(conteudoLinha, 162))
                    .conselhoEscolar(stringToBoolean(conteudoLinha, 163))
                    .gremioEstudantil(stringToBoolean(conteudoLinha, 164))
                    .outros(stringToBoolean(conteudoLinha, 165))
                    .isOrgaosColegiadosEmFuncionamento(stringToBoolean(conteudoLinha, 166))
                    //.projetoPoliticoLeiLdb12(stringToBoolean(conteudoLinha, 167))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private ReservaVagasSistemaCotas getReservaVagasSistemaCotas(String[] conteudoLinha,
                                                                 Optional<UnidadeEnsino> unidadeOptional) {
        try {

            var reservaVagasSistemaCotasOptional = unidadeOptional
                    .map(UnidadeEnsino::getReservaVagasSistemaCotas)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  ReservaVagasSistemaCotas
                    .builder()
                    .id(reservaVagasSistemaCotasOptional.map(ReservaVagasSistemaCotas::getId).orElse(null))
                    .autoDeclaradoPretoPardoOuIndigena(stringToBoolean(conteudoLinha, 152))
                    .condicaoDeRenda(stringToBoolean(conteudoLinha, 153))
                    .oriundoEscolaPublica(stringToBoolean(conteudoLinha, 154))
                    .pessoaComDeficiencia(stringToBoolean(conteudoLinha, 155))
                    .outrosGruposNaoListados(stringToBoolean(conteudoLinha, 156))
                    .isReservaVagasCotas(stringToBoolean(conteudoLinha, 157))
                    .escolaPossuiRedesPComunicacaoInstitucional(stringToBoolean(conteudoLinha, 158))
                    .escolaCompartilhaEspacosComunidade(stringToBoolean(conteudoLinha, 159))
                    .espacoUsadoAtividadeAlunos(stringToBoolean(conteudoLinha, 160))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private IdiomaEnsino getIdiomaEnsino(String[] conteudoLinha, Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var idiomaEnsinoOptional = unidadeOptional
                    .map(UnidadeEnsino::getIdiomaEnsino)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  IdiomaEnsino
                    .builder()
                    .id(idiomaEnsinoOptional.map(IdiomaEnsino::getId).orElse(null))
                    .linguaIndigena(stringToBoolean(conteudoLinha, 146))
                    .linguaPortuguesa(stringToBoolean(conteudoLinha, 147))
                    /**.codigoLinguaIndigena1(getLinguaIndigena(stringToLong(conteudoLinha, 148)))
                     **.codigoLinguaIndigena2(getLinguaIndigena(stringToLong(conteudoLinha, 149)))
                     **.codigoLinguaIndigena3(getLinguaIndigena(stringToLong(conteudoLinha, 150)))
                     **/
                    .realizaExamesAvaliacaoAlunos(stringToBoolean(conteudoLinha, 151))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private LinguaIndigena getLinguaIndigena(Long codigoLinguaIndegena) {
        return linguaIndigenaRepository.findById(codigoLinguaIndegena).orElse(null);
    }

    private InstrumentosMateriaisSocioCulturais getInstrumentosMateriaisSocioCulturais(String[] conteudoLinha,
                                                                                       Optional<UnidadeEnsino> unidadeOptional) {
        try {

            var instrumentosMateriaisSocioCulturais = unidadeOptional
                    .map(UnidadeEnsino::getInstrumentosMateriaisSocioCulturais)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  InstrumentosMateriaisSocioCulturais
                    .builder()
                    .id(instrumentosMateriaisSocioCulturais.map(InstrumentosMateriaisSocioCulturais::getId).orElse(null))
                    .acervoMultimidia(stringToBoolean(conteudoLinha, 132))
                    .brinquedosEducacaoInfantil(stringToBoolean(conteudoLinha, 133))
                    .conjuntoMateriaisCientificos(stringToBoolean(conteudoLinha, 134))
                    .equipamentoAmplificacao(stringToBoolean(conteudoLinha, 135))
                    .instrumentosMusicais(stringToBoolean(conteudoLinha, 136))
                    .jogosEducativos(stringToBoolean(conteudoLinha, 137))
                    .materiaisAtividadesCulturais(stringToBoolean(conteudoLinha, 138))
                    .materiaisEducacaoProfissional(stringToBoolean(conteudoLinha, 139))
                    .materiaisPraticaDesportivaRecreacao(stringToBoolean(conteudoLinha, 140))
                    .materiaisPedagogicosEducacaoIndigena(stringToBoolean(conteudoLinha, 141))
                    .materiaisPedagogicosEducacaoEtnicosRaciais(stringToBoolean(conteudoLinha, 142))
                    .materiaisPedagogicosEducacaoCampo(stringToBoolean(conteudoLinha, 143))
                    .nenhumInstrumentosListados(stringToBoolean(conteudoLinha, 144))
                    .educacaoEscolarIndigena(stringToBoolean(conteudoLinha, 145))
                    .build();


        }catch (Exception e) {
            return null;
        }
    }

    private TotalProfissionaisAtivosEscola getTotalProfissionaisAtivosEscola(String[] conteudoLinha,
                                                                             Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var totalProfissionaisAtivosEscolaOptional = unidadeOptional
                    .map(UnidadeEnsino::getTotalProfissionaisAtivosEscola)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  TotalProfissionaisAtivosEscola
                    .builder()
                    .id(totalProfissionaisAtivosEscolaOptional.map(TotalProfissionaisAtivosEscola::getId).orElse(null))
                    .auxiliaresSecretaria(stringToInteger(conteudoLinha, 115))
                    .auxiliarServicosGerais(stringToInteger(conteudoLinha, 116))
                    .bibliotecarioAuxiliarMonitor(stringToInteger(conteudoLinha,  117))
                    .bombeiroBrigadista(stringToInteger(conteudoLinha,  118))
                    .coordenadorTurnoDisciplinar(stringToInteger(conteudoLinha,  119))
                    .fonoaudiologo(stringToInteger(conteudoLinha,  120))
                    .nutricionista(stringToInteger(conteudoLinha,  121))
                    .psicologoEscolar(stringToInteger(conteudoLinha,  122))
                    .profissionaisPreparacao(stringToInteger(conteudoLinha,  123))
                    .profissionaisApoioSupervisao(stringToInteger(conteudoLinha,  124))
                    .secretarioEscolar(stringToInteger(conteudoLinha,  125))
                    .segurancaGuardaOuSegurancaPatrimonial(stringToInteger(conteudoLinha,  126))
                    .auxiliaresLaboratorio(stringToInteger(conteudoLinha,  127))
                    .viceDiretor(stringToInteger(conteudoLinha,  128))
                    .orientadorComunitario(stringToInteger(conteudoLinha,  129))
                    .isProfissioaisParaFuncoesListadas(stringToBoolean(conteudoLinha,  130))
                    .alimentacaoEscolarParaAluno(stringToBoolean(conteudoLinha,  131))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private RedeLocalInterligacaoComputadores getRedeLocalInterligacaoComputadores(String[] conteudoLinha,
                                                                                   Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var redeLocalInterligacaoComputadoresOptional = unidadeOptional
                    .map(UnidadeEnsino::getRedeLocalInterligacaoComputadores)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return RedeLocalInterligacaoComputadores
                    .builder()
                    .id(redeLocalInterligacaoComputadoresOptional.map(RedeLocalInterligacaoComputadores::getId).orElse(null))
                    .cabo(stringToBoolean(conteudoLinha, 112))
                    .wireless(stringToBoolean(conteudoLinha, 113))
                    .naoHaRedeLocalInterligacap(stringToBoolean(conteudoLinha, 114))
                    .build();


        }catch (Exception e) {
            return null;
        }
    }

    private EquipamentosUsadosAlunosAcessoInternet getEquipamentosUsadosAlunosAcessosInternet(String[] conteudoLinha,
                                                                                              Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var equipamentosUsadosAlunosAcessoInternetOptional = unidadeOptional
                    .map(UnidadeEnsino::getEquipamentosUsadosAlunosAcessoInternet)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  EquipamentosUsadosAlunosAcessoInternet
                    .builder()
                    .id(equipamentosUsadosAlunosAcessoInternetOptional.map(EquipamentosUsadosAlunosAcessoInternet::getId).orElse(null))
                    .computadoresTabletsBibliotecaUnidade(stringToBoolean(conteudoLinha, 109))
                    .dispositivosPessoaisComputadoresPortateis(stringToBoolean(conteudoLinha, 110))
                    .internetBandaLarga(stringToBoolean(conteudoLinha, 111))
                    .build();

        }catch (Exception e) {
            return null;
        }

    }

    private AcessoInternet getAcessoInternet(String[] conteudoLinha,
                                             Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var acessoInternetOptional = unidadeOptional
                    .map(UnidadeEnsino::getAcessoInternet)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  AcessoInternet
                    .builder()
                    .id(acessoInternetOptional.map(AcessoInternet::getId).orElse(null))
                    .paraUsoAdministrativo(stringToBoolean(conteudoLinha, 104))
                    .paraUsoProcessoApredizagem(stringToBoolean(conteudoLinha, 105))
                    .paraUsoAlunos(stringToBoolean(conteudoLinha, 105))
                    .paraUsoComunidade(stringToBoolean(conteudoLinha, 106))
                    .naoPossuiAcessoInternet(stringToBoolean(conteudoLinha,107))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private QuantidadeComputadoresEmUsoAlunos getQuantidadeComputadoresEmUsoPeloAlunos(String[] conteudoLinha,
                                                                                       Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var quantidadeComputadoresEmUsoAlunosOptional = unidadeOptional
                    .map(UnidadeEnsino::getQuantidadeComputadoresEmUsoAlunos)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  QuantidadeComputadoresEmUsoAlunos
                    .builder()
                    .id(quantidadeComputadoresEmUsoAlunosOptional.map(QuantidadeComputadoresEmUsoAlunos::getId).orElse(null))
                    .computadoresMesaDesktop(stringToLong(conteudoLinha, 101))
                    .computadoresPortateis(stringToLong(conteudoLinha, 102))
                    .tablets(stringToLong(conteudoLinha, 103))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }
    private QuantidadeEquipamentosProcessoAprendizagem getQuantidadeEquipamentosUsadosProcessoAprendizagem(String[] conteudoLinha,
                                                                                                           Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var quantidadeEquipamentosProcessoAprendizagemOptional = unidadeOptional
                    .map(UnidadeEnsino::getQuantidadeEquipamentosProcessoAprendizagem)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  QuantidadeEquipamentosProcessoAprendizagem
                    .builder()
                    .id(quantidadeEquipamentosProcessoAprendizagemOptional.map(QuantidadeEquipamentosProcessoAprendizagem::getId).orElse(null))
                    .aparelhoDvdBluray(stringToInteger(conteudoLinha, 96))
                    .aparelhoSom(stringToInteger(conteudoLinha, 97))
                    .aparelhoTelevisao(stringToInteger(conteudoLinha, 98))
                    .lousaDigital(stringToInteger(conteudoLinha, 99))
                    .projetorMultimidiaDataShow(stringToInteger(conteudoLinha, 100))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private EquipamentosExistentesUnidade getEquipamentosExistentes(String[] conteudoLinha,
                                                                    Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var equipamentosExistentesUnidadeOptional = unidadeOptional
                    .map(UnidadeEnsino::getEquipamentosExistentesUnidade)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  EquipamentosExistentesUnidade
                    .builder()
                    .id(equipamentosExistentesUnidadeOptional.map(EquipamentosExistentesUnidade::getId).orElse(null))
                    .antenaParabolica(stringToBoolean(conteudoLinha, 89))
                    .computadores(stringToBoolean(conteudoLinha, 90))
                    .copiadora(stringToBoolean(conteudoLinha, 91))
                    .impressora(stringToBoolean(conteudoLinha, 92))
                    .impressoraMultifuncional(stringToBoolean(conteudoLinha, 93))
                    .scanner(stringToBoolean(conteudoLinha, 94))
                    .nenhumDosEquipamentosListados(stringToBoolean(conteudoLinha, 95))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private RecursoPessoasDeficientes getRecursosPessoasDeficientes(String[] conteudoLinha,
                                                                    Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var recursoPessoasDeficientes = unidadeOptional
                    .map(UnidadeEnsino::getRecursoPessoasDeficientes)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  RecursoPessoasDeficientes
                    .builder()
                    .id(recursoPessoasDeficientes.map(RecursoPessoasDeficientes::getId).orElse(null))
                    .corrimaoGuardaCorpos(stringToBoolean(conteudoLinha, 76))
                    .elevador(stringToBoolean(conteudoLinha, 77))
                    .pisosTateis(stringToBoolean(conteudoLinha, 78))
                    .portasVaoLivre(stringToBoolean(conteudoLinha, 79))
                    .rampas(stringToBoolean(conteudoLinha, 80))
                    .sinalizacaoSonora(stringToBoolean(conteudoLinha, 81))
                    .sinalizacaoTatil(stringToBoolean(conteudoLinha, 82))
                    .sinalizacaoVisual(stringToBoolean(conteudoLinha, 83))
                    .nenhumRecursosAcessibilidade(stringToBoolean(conteudoLinha, 84))
                    .numeroSalasUtilizadasPredio(stringToInteger(conteudoLinha, 85))
                    .numeroSalasAulaUtilizadasEscolaForaPredio(stringToInteger(conteudoLinha, 86))
                    .numeroSalasClimatizadas(stringToInteger(conteudoLinha, 87))
                    .numeroSalasAcessivelPessoasDeficientes(stringToInteger(conteudoLinha, 88))
                    //.unidadeEnsino(unidadeEnsinoConsultada)
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private DependenciasFisicas getDependenciasFisicas(String[] conteudoLinha, Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var dependenciasFisicasOptional = unidadeOptional
                    .map(UnidadeEnsino::getDependenciasFisicas)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  DependenciasFisicas
                    .builder()
                    .id(dependenciasFisicasOptional.map(DependenciasFisicas::getId).orElse(null))
                    .almoxarifado(stringToBoolean(conteudoLinha, 39))
                    .areaVerde(stringToBoolean(conteudoLinha, 40))
                    .auditorio(stringToBoolean(conteudoLinha, 41))
                    .banheiro(stringToBoolean(conteudoLinha, 42))
                    .banheiroAcessivelPessoasDeficiencia(stringToBoolean(conteudoLinha, 43))
                    .banheiroAdequadoEducacaoInfantil(stringToBoolean(conteudoLinha, 44))
                    .banheiroExclusivoParaFuncionarios(stringToBoolean(conteudoLinha, 45))
                    .banheiroOuVestiarioComChuveiro(stringToBoolean(conteudoLinha, 46))
                    .biblioteca(stringToBoolean(conteudoLinha, 47))
                    .cozinha(stringToBoolean(conteudoLinha, 48))
                    .despensa(stringToBoolean(conteudoLinha, 49))
                    .dormitorioAluno(stringToBoolean(conteudoLinha, 50))
                    .dormitorioProfessora(stringToBoolean(conteudoLinha, 51))
                    .laboratorioCiencias(stringToBoolean(conteudoLinha, 52))
                    .laboratorioInformatica(stringToBoolean(conteudoLinha, 53))
                    .laboratorioEspecificoEducacaoProfissional(stringToBoolean(conteudoLinha, 54))
                    .parqueInfantil(stringToBoolean(conteudoLinha, 55))
                    .patioCoberto(stringToBoolean(conteudoLinha, 56))
                    .patioDescoberto(stringToBoolean(conteudoLinha, 57))
                    .piscina(stringToBoolean(conteudoLinha, 58))
                    .quadraEsportesCoberta(stringToBoolean(conteudoLinha, 59))
                    .quadraEsportesDescoberta(stringToBoolean(conteudoLinha, 60))
                    .refeitorio(stringToBoolean(conteudoLinha, 61))
                    .salaRepousoParaAluno(stringToBoolean(conteudoLinha, 62))
                    .salaAtelieArtes(stringToBoolean(conteudoLinha, 63))
                    .salaMusicaCoral(stringToBoolean(conteudoLinha, 64))
                    .salaEstudioDanca(stringToBoolean(conteudoLinha, 65))
                    .salaMultiusoMusicaDancaArtes(stringToBoolean(conteudoLinha, 66))
                    .terreiroParaRecreacao(stringToBoolean(conteudoLinha, 67))
                    .viveiroCriacaoAnimais(stringToBoolean(conteudoLinha, 68))
                    .salaDiretoria(stringToBoolean(conteudoLinha, 69))
                    .salaLeitura(stringToBoolean(conteudoLinha, 70))
                    .salaProfessores(stringToBoolean(conteudoLinha, 71))
                    .salaRecursosMultifuncionais(stringToBoolean(conteudoLinha, 72))
                    .salaSecretaria(stringToBoolean(conteudoLinha, 73))
                    .salasOficinasEducacaoProfissional(stringToBoolean(conteudoLinha, 74))
                    .nenhumaDasDependenciasRelacionadas(stringToBoolean(conteudoLinha, 75))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private TratamentoLixo getTratamentoLixo(String[] conteudoLinha,
                                             Optional<UnidadeEnsino> unidadeOptional) {
        try {

            var tratamentoLixoOptional = unidadeOptional
                    .map(UnidadeEnsino::getTratamentoLixo)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  TratamentoLixo
                    .builder()
                    .id(tratamentoLixoOptional.map(TratamentoLixo::getId).orElse(null))
                    .separacaoDoLixoResiduos(stringToBoolean(conteudoLinha, 35))
                    .reaproveitamentoReutilizacao(stringToBoolean(conteudoLinha, 36))
                    .reciclagem(stringToBoolean(conteudoLinha, 37))
                    .naoFazTratamento(stringToBoolean(conteudoLinha, 38))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private DestinacaoLixo getDestinacaoLixo(String[] conteudoLinha, Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var destinacaoLixoOptional = unidadeOptional
                    .map(UnidadeEnsino::getDestinacaoLixo)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  DestinacaoLixo
                    .builder()
                    .id(destinacaoLixoOptional.map(DestinacaoLixo::getId).orElse(null))
                    .servicoColeta(stringToBoolean(conteudoLinha, 30))
                    .queima(stringToBoolean(conteudoLinha, 31))
                    .enterra(stringToBoolean(conteudoLinha, 32))
                    .levaDestinacaoLicenciadaPoderPublico(stringToBoolean(conteudoLinha, 33))
                    .descartaEmOutraArea(stringToBoolean(conteudoLinha, 34))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private EsgotamentoSanitario getEsgotamentoSanitario(String[] conteudoLinha,
                                                         Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var esgotamentoSanitarioOptional = unidadeOptional
                    .map(UnidadeEnsino::getEsgotamentoSanitario)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  EsgotamentoSanitario
                    .builder()
                    .id(esgotamentoSanitarioOptional.map(EsgotamentoSanitario::getId).orElse(null))
                    .redePublica(stringToBoolean(conteudoLinha, 26))
                    .fossaSeptica(stringToBoolean(conteudoLinha, 27))
                    .fossaRudimentarComum(stringToBoolean(conteudoLinha, 28))
                    .naoHaEsgotamentoSanitario(stringToBoolean(conteudoLinha, 29))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private FonteEnergiaEletrica getFonteEnergiaEletrica(String[] conteudoLinha,
                                                         Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var fonteEnergiaEletricaOptional = unidadeOptional
                    .map(UnidadeEnsino::getFonteEnergiaEletrica)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  FonteEnergiaEletrica
                    .builder()
                    .id(fonteEnergiaEletricaOptional.map(FonteEnergiaEletrica::getId).orElse(null))
                    .redePublica(stringToBoolean(conteudoLinha, 22))
                    .geradorMovidoACombustivelFossil(stringToBoolean(conteudoLinha, 23))
                    .fontesEnergiaRenovaveisAlternativas(stringToBoolean(conteudoLinha, 24))
                    .naoHaEnergiaEletrica(stringToBoolean(conteudoLinha, 25))
                    .build();

        } catch (Exception e) {
            return null;
        }
    }

    private AbastecimentoAgua getAbastecimentoAgua(String[] conteudoLinha,
                                                   Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var abastecimentoAguaOptional = unidadeOptional
                    .map(UnidadeEnsino::getAbastecimentoAgua)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  AbastecimentoAgua
                    .builder()
                    .id(abastecimentoAguaOptional.map(AbastecimentoAgua::getId).orElse(null))
                    .redePublica(stringToBoolean(conteudoLinha, 17))
                    .pocoArtesiano(stringToBoolean(conteudoLinha, 18))
                    .cacimbaCisternaPoco(stringToBoolean(conteudoLinha, 19))
                    .fonteRioIgarapeRiachoCorrego(stringToBoolean(conteudoLinha, 20))
                    .naoHaAbastecimentoAgua(stringToBoolean(conteudoLinha, 21))
                    .build();

        }catch (Exception e) {
            return null;
        }
    }

    private EscolasComQualCompartilha getEscolaComQualCompartilha(String[] conteudoLinha,
                                                                  Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var escolasComQualCompartilhaOptional = unidadeOptional
                    .map(UnidadeEnsino::getEscolasComQualCompartilha)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  EscolasComQualCompartilha
                    .builder()
                    .id(escolasComQualCompartilhaOptional.map(EscolasComQualCompartilha::getId).orElse(null))
                    .codigoEscolaComQualCompartilha1(getUnidadeEnsinoPeloCodigoInep(stringToLong(conteudoLinha, 10)))
                    .codigoEscolaComQualCompartilha2(getUnidadeEnsinoPeloCodigoInep(stringToLong(conteudoLinha, 11)))
                    .codigoEscolaComQualCompartilha3(getUnidadeEnsinoPeloCodigoInep(stringToLong(conteudoLinha, 12)))
                    .codigoEscolaComQualCompartilha4(getUnidadeEnsinoPeloCodigoInep(stringToLong(conteudoLinha, 13)))
                    .codigoEscolaComQualCompartilha5(getUnidadeEnsinoPeloCodigoInep(stringToLong(conteudoLinha, 14)))
                    .codigoEscolaComQualCompartilha6(getUnidadeEnsinoPeloCodigoInep(stringToLong(conteudoLinha, 15)))
                    .forneceAguaPotavelParaConsumo(stringToBoolean(conteudoLinha, 17))
                    .build();

        } catch (Exception e) {
            return new EscolasComQualCompartilha();
        }
    }

    private UnidadeEnsino getUnidadeEnsinoPeloCodigoInep(Long cdInep) {
        return  Optional.ofNullable(cdInep).map(codigo -> unidadeEnsinoRepository.findById(codigo).orElse(null)).orElse(null);
    }

    private LocalFuncionamentoEscola getLocalFuncionamentoEscola(String[] conteudoLinha, Optional<UnidadeEnsino> unidadeOptional) {
        try {
            var localFuncionamentoUnidadeConsultada = unidadeOptional
                    .map(UnidadeEnsino::getLocalFuncionamentoEscola)
                    .filter(Objects::nonNull)
                    .stream()
                    .findAny();

            return  LocalFuncionamentoEscola
                    .builder()
                    .id(localFuncionamentoUnidadeConsultada.map(LocalFuncionamentoEscola::getId).orElse(null))
                    .predioEscolar(stringToBoolean(conteudoLinha, 2))
                    .salasEmOutraEscola(stringToBoolean(conteudoLinha, 3))
                    .galpaoRanchoPaiolBarracao(stringToBoolean(conteudoLinha, 4))
                    .unidadeAtendimentoSocioeducativa(stringToBoolean(conteudoLinha, 5))
                    .unidadePrisional(stringToBoolean(conteudoLinha, 6))
                    .outros(stringToBoolean(conteudoLinha, 7))
                    .formaOcupacaoPredio(getFormaOcupacaoPredio(valorString(conteudoLinha, 8)))
                    .predioEscolarCompartilhadoComOutraEscola(stringToBoolean(conteudoLinha, 9))
                    .predioEscolarCompartilhadoComOutraEscola(stringToBoolean(conteudoLinha, 9))
                    .build();

        }catch (ArrayIndexOutOfBoundsException  | NumberFormatException e) {
            return null;
        }
    }

    private FormaOcupacaoPredio getFormaOcupacaoPredio(String conteudo) {
        return (FormaOcupacaoPredio) buscaRegistroConteudoLido(conteudo, FormaOcupacaoPredio.values());
    }

    private UnidadeEnsino atualizarDadosUnidadeEnsino(String[] conteudoLinha) {
        return Optional.ofNullable(unidadeEnsinoRepository.findById(stringToLong(conteudoLinha, 1)))
                .map(unidadeEnsinoConsultada -> atualizarDadosUnidadeEnsinoConsultada(unidadeEnsinoConsultada, conteudoLinha))
                .orElse(atualizarDadosUnidadeEnsinoNaoConsultada(conteudoLinha));
    }

    private UnidadeEnsino atualizarDadosUnidadeEnsinoNaoConsultada(String[] conteudoLinha) {
        return getDadosDaUnidadeEnsinoNaLinha(conteudoLinha, Optional.empty());
    }

    private UnidadeEnsino salvarDadosUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        return unidadeEnsinoRepository.save(unidadeEnsino);
    }

    private UnidadeEnsino atualizarDadosUnidadeEnsinoConsultada(Optional<UnidadeEnsino> unidadeEnsino,
                                                                String[] conteudoLinha) {
        return getDadosDaUnidadeEnsinoNaLinha(conteudoLinha, unidadeEnsino);
    }

}
