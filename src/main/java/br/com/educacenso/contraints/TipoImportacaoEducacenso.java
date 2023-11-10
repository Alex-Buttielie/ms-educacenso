package br.com.educacenso.contraints;

import br.com.educacenso.repositories.AreaConhecimentoRepository;
import br.com.educacenso.repositories.AreaPosGraduacaoRepository;
import br.com.educacenso.repositories.FormacaoComplementarPedagogicaProfessorRepository;
import br.com.educacenso.repositories.OutrosCursosEspecificosRepository;
import br.com.educacenso.repositories.PosGraduacaoConcluidaProfessorRepository;
import br.com.educacenso.repositories.TipoDeficienciaEspectroAltasHabilidadesRepository;
import br.com.educacenso.repositories.PessoaRepository;
import br.com.educacenso.services.impl.ExecutarImportacaoTurmasServiceImpl;
import br.com.educacenso.repositories.TurmaRepository;
import br.com.educacenso.repositories.AbastecimentoAguaRespository;
import br.com.educacenso.repositories.AcessoInternetRepository;
import br.com.educacenso.repositories.DependenciasFisicasRepository;
import br.com.educacenso.repositories.DestinacaoLixoRepository;
import br.com.educacenso.repositories.EquipamentosExistentesUnidadeRepository;
import br.com.educacenso.repositories.EquipamentosUsadosAlunosParaAcessoInternetRepository;
import br.com.educacenso.repositories.EscolasComQualCompartilhaRepository;
import br.com.educacenso.repositories.EsgotamentoSanitarioRepository;
import br.com.educacenso.repositories.FonteEnergiaEletricaRepository;
import br.com.educacenso.repositories.IdiomaEnsinoRepository;
import br.com.educacenso.repositories.InstrumentosMateriaisSocioCulturaisRepository;
import br.com.educacenso.repositories.LinguaIndigenaRepository;
import br.com.educacenso.repositories.LocalFuncionamentoEscolaRepository;
import br.com.educacenso.repositories.OrgaosColegiadosFuncionamentoEscolaRepository;
import br.com.educacenso.repositories.QuantidadeComputadoresEmUsoAlunosRepository;
import br.com.educacenso.repositories.QuantidadeEquipamentosProcessoApredizagemRepository;
import br.com.educacenso.repositories.RecursoAlunoParaAvaliacaoInepRepository;
import br.com.educacenso.repositories.RecursoPessoasDeficientesRepository;
import br.com.educacenso.repositories.RedeLocalInterligacaoComputadoresRepository;
import br.com.educacenso.repositories.ReservaVagasSistemaCotasRepository;
import br.com.educacenso.repositories.TotalProfissionaisAtivosEscolaRepository;
import br.com.educacenso.repositories.TratamentoLixoRepository;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
import br.com.educacenso.services.ExecutarImportacaoDocentesDadosPessoaisService;
import br.com.educacenso.services.ExecutarImportacaoService;
import br.com.educacenso.services.ExecutarImportacaoUnidadesEnsinoIdentificacaoService;
import br.com.educacenso.services.ExecutarImportacaoUnidadesEnsinoInfraestruturaService;
import br.com.educacenso.services.impl.ExecutarImportacaoDocentesDadosPessoaisServiceImpl;
import br.com.educacenso.services.impl.ExecutarImportacaoUnidadesEnsinoIdentificacaoServiceImpl;
import br.com.educacenso.services.impl.ExecutarImportacaoUnidadesEnsinoInfraestruturaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

public enum TipoImportacaoEducacenso {

    UNIDADE_ENSINO {
        @Override
        public ExecutarImportacaoUnidadesEnsinoIdentificacaoService getTipoImportacao() {
            return new ExecutarImportacaoUnidadesEnsinoIdentificacaoServiceImpl(this.unidadeEnsinoRepository) {
            };
        }
    },
    UNIDADE_ENSINO_COMPLEMENTAR {
        @Override
        public ExecutarImportacaoUnidadesEnsinoInfraestruturaService getTipoImportacao() {
            return new ExecutarImportacaoUnidadesEnsinoInfraestruturaServiceImpl(this.unidadeEnsinoRepository,this.linguaIndigenaRepository);
        }
    },
    TURMA {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoTurmasServiceImpl(this.turmaRepository, this.unidadeEnsinoRepository);
        }
    },
    DOCENTE_DADOS_PESSOAS() {
        @Override
        public ExecutarImportacaoDocentesDadosPessoaisService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    },
    DOCENTE_COMPLEMENTAR_1 {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    },
    DOCENTE_COMPLEMENTAR_2 {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    },
    DOCENTE_COMPLEMENTAR_3 {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    },
    DOCENTE_COMPLEMENTAR_4 {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    },
    ALUNO_COMPLEMENTAR_1 {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    },
    ALUNO_COMPLEMENTAR_2 {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    },
    ALUNO_COMPLEMENTAR_3 {
        @Override
        public ExecutarImportacaoService getTipoImportacao() {
            return new ExecutarImportacaoDocentesDadosPessoaisServiceImpl(this.pessoaRepository, this.unidadeEnsinoRepository,
                    this.areaConhecimentoRepository, this.areaPosGraduacaoRepository);
        }
    };

    protected PessoaRepository pessoaRepository;
    protected TurmaRepository turmaRepository;
    protected UnidadeEnsinoRepository unidadeEnsinoRepository;
    protected AreaConhecimentoRepository areaConhecimentoRepository;
    protected AreaPosGraduacaoRepository areaPosGraduacaoRepository;
    protected FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository;
    protected TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository;
    protected OutrosCursosEspecificosRepository outrosCursosEspecificosRepository;
    protected RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository;
    protected PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository;
    protected LocalFuncionamentoEscolaRepository localFuncionamentoEscolaRepository;
    protected EscolasComQualCompartilhaRepository escolasComQualCompartilhaRepository;
    protected AbastecimentoAguaRespository abastecimentoAguaRespository;
    protected FonteEnergiaEletricaRepository fonteEnergiaEletricaRepository;
    protected EsgotamentoSanitarioRepository esgotamentoSanitarioRepository;
    protected DestinacaoLixoRepository destinacaoLixoRepository;
    protected TratamentoLixoRepository tratamentoLixoRepository;
    protected DependenciasFisicasRepository dependenciasFisicasRepository;
    protected RecursoPessoasDeficientesRepository recursoPessoasDeficientesRepository;
    protected EquipamentosExistentesUnidadeRepository equipamentosExistentesUnidadeRepository;
    protected QuantidadeEquipamentosProcessoApredizagemRepository quantidadeEquipamentosProcessoApredizagemRepository;
    protected AcessoInternetRepository acessoInternetRepository;
    protected EquipamentosUsadosAlunosParaAcessoInternetRepository equipamentosUsadosAlunosParaAcessoInternetRepository;
    protected RedeLocalInterligacaoComputadoresRepository redeLocalInterligacaoComputadoresRepository;
    protected TotalProfissionaisAtivosEscolaRepository totalProfissionaisAtivosEscolaRepository;
    protected InstrumentosMateriaisSocioCulturaisRepository instrumentosMateriaisSocioCulturaisRepository;
    protected IdiomaEnsinoRepository idiomaEnsinoRepository;
    protected LinguaIndigenaRepository linguaIndigenaRepository;
    protected ReservaVagasSistemaCotasRepository reservaVagasSistemaCotasRepository;
    protected OrgaosColegiadosFuncionamentoEscolaRepository orgaosColegiadosFuncionamentoEscolaRepository;
    protected QuantidadeComputadoresEmUsoAlunosRepository quantidadeComputadoresEmUsoAlunosRepository;

    public abstract ExecutarImportacaoService getTipoImportacao();

    private void setPessoaRepository(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }
    private void setUnidadeEnsinoRepository(UnidadeEnsinoRepository unidadeEnsinoRepository) {
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
    }
    private void setAreaConhecimentoRepository(AreaConhecimentoRepository areaConhecimentoRepository) {
        this.areaConhecimentoRepository = areaConhecimentoRepository;
    }
    private void setTurma(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }
    private void setAreaPosGraduacao(AreaPosGraduacaoRepository areaPosGraduacaoRepository) {
        this.areaPosGraduacaoRepository = areaPosGraduacaoRepository;
    }
    private void setFormacaoComplementarPedagogicaProfessorRepository(FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository) {
        this.formacaoComplementarPedagogicaProfessorRepository = formacaoComplementarPedagogicaProfessorRepository;
    }
    private void setRecursoAlunoParaAvaliacaoInepRepository(RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository) {
        this.recursoAlunoParaAvaliacaoInepRepository= recursoAlunoParaAvaliacaoInepRepository;
    }
    private void setOutrosCursosEspecificosRepository(OutrosCursosEspecificosRepository outrosCursosEspecificosRepository) {
        this.outrosCursosEspecificosRepository= outrosCursosEspecificosRepository;
    }
    private void setTipoDeficienciaEspectroAltasHabilidadesRepository(TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository) {
        this.tipoDeficienciaEspectroAltasHabilidadesRepository= tipoDeficienciaEspectroAltasHabilidadesRepository;
    }
    private void setPosGraduacaoConcluidaProfessorRepository(PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository) {
        this.posGraduacaoConcluidaProfessorRepository = posGraduacaoConcluidaProfessorRepository;
    }

    private void setLocalFuncionamentoEscolaRepository(LocalFuncionamentoEscolaRepository localFuncionamentoEscolaRepository) {
        this.localFuncionamentoEscolaRepository = localFuncionamentoEscolaRepository;
    }
    private void setEscolasComQualCompartilhaRepository(EscolasComQualCompartilhaRepository escolasComQualCompartilhaRepository) {
        this.escolasComQualCompartilhaRepository = escolasComQualCompartilhaRepository;
    }
    private void setAbastecimentoAguaRespository(AbastecimentoAguaRespository abastecimentoAguaRespository) {
        this.abastecimentoAguaRespository = abastecimentoAguaRespository;
    }
    private void setEsgotamentoSanitarioRepository(EsgotamentoSanitarioRepository esgotamentoSanitarioRepository) {
        this.esgotamentoSanitarioRepository = esgotamentoSanitarioRepository;
    }
    private void setFonteEnergiaEletricaRepository(FonteEnergiaEletricaRepository fonteEnergiaEletricaRepository) {
        this.fonteEnergiaEletricaRepository = fonteEnergiaEletricaRepository;
    }
    private void setDestinacaoLixo(DestinacaoLixoRepository destinacaoLixoRepository) {
        this.destinacaoLixoRepository = destinacaoLixoRepository;
    }
    private void setTratamentoLixoRepository(TratamentoLixoRepository tratamentoLixoRepository) {
        this.tratamentoLixoRepository = tratamentoLixoRepository;
    }
    private void setDependenciasFisicasRepository(DependenciasFisicasRepository dependenciasFisicasRepository) {
        this.dependenciasFisicasRepository = dependenciasFisicasRepository;
    }
    private void setEquipamentosExistentesUnidadeRepository(EquipamentosExistentesUnidadeRepository equipamentosExistentesUnidadeRepository) {
        this.equipamentosExistentesUnidadeRepository = equipamentosExistentesUnidadeRepository;
    }
    private void setRecursoPessoasDeficientesRepository(RecursoPessoasDeficientesRepository recursoPessoasDeficientesRepository) {
        this.recursoPessoasDeficientesRepository = recursoPessoasDeficientesRepository;
    }
    private void setOrgaosColegiadosFuncionamentoEscolaRepository(OrgaosColegiadosFuncionamentoEscolaRepository orgaosColegiadosFuncionamentoEscolaRepository) {
        this.orgaosColegiadosFuncionamentoEscolaRepository = orgaosColegiadosFuncionamentoEscolaRepository;
    }
    private void setQuantidadeComputadoresEmUsoAlunosRepository(QuantidadeComputadoresEmUsoAlunosRepository quantidadeComputadoresEmUsoAlunosRepository) {
        this.quantidadeComputadoresEmUsoAlunosRepository = quantidadeComputadoresEmUsoAlunosRepository;
    }
    private void setReservaVagasSistemaCotasRepository(ReservaVagasSistemaCotasRepository reservaVagasSistemaCotasRepository) {
        this.reservaVagasSistemaCotasRepository = reservaVagasSistemaCotasRepository;
    }
    private void setLinguaIndigenaRepository(LinguaIndigenaRepository linguaIndigenaRepository) {
        this.linguaIndigenaRepository = linguaIndigenaRepository;
    }
    private void setIdiomaEnsinoRepository(IdiomaEnsinoRepository idiomaEnsinoRepository) {
        this.idiomaEnsinoRepository = idiomaEnsinoRepository;
    }
    private void setInstrumentosMateriaisSocioCulturaisRepository(InstrumentosMateriaisSocioCulturaisRepository instrumentosMateriaisSocioCulturaisRepository) {
        this.instrumentosMateriaisSocioCulturaisRepository = instrumentosMateriaisSocioCulturaisRepository;
    }
    private void setTotalProfissionaisAtivosEscolaRepository(TotalProfissionaisAtivosEscolaRepository totalProfissionaisAtivosEscolaRepository) {
        this.totalProfissionaisAtivosEscolaRepository = totalProfissionaisAtivosEscolaRepository;
    }
    private void setRedeLocalInterligacaoComputadoresRepository(RedeLocalInterligacaoComputadoresRepository redeLocalInterligacaoComputadoresRepository) {
        this.redeLocalInterligacaoComputadoresRepository = redeLocalInterligacaoComputadoresRepository;
    }
    private void setEquipamentosUsadosAlunosParaAcessoInternetRepository(EquipamentosUsadosAlunosParaAcessoInternetRepository equipamentosUsadosAlunosParaAcessoInternetRepository) {
        this.equipamentosUsadosAlunosParaAcessoInternetRepository = equipamentosUsadosAlunosParaAcessoInternetRepository;
    }
    private void setAcessoInternetRepository(AcessoInternetRepository acessoInternetRepository) {
        this.acessoInternetRepository = acessoInternetRepository;
    }
    private void setQuantidadeEquipamentosProcessoApredizagemRepository(QuantidadeEquipamentosProcessoApredizagemRepository quantidadeEquipamentosProcessoApredizagemRepository) {
        this.quantidadeEquipamentosProcessoApredizagemRepository = quantidadeEquipamentosProcessoApredizagemRepository;
    }

    @Component
    static class ServiceInjector {

        @Autowired
        private PessoaRepository pessoaRepository;

        @Autowired
        private UnidadeEnsinoRepository unidadeEnsinoRepository;

        @Autowired
        private AreaConhecimentoRepository areaConhecimentoRepository;

        @Autowired
        private TurmaRepository turmaRepository;

        @Autowired
        private AreaPosGraduacaoRepository areaPosGraduacaoRepository;

        @Autowired
        private FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository;

        @Autowired
        protected TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository;

        @Autowired
        protected OutrosCursosEspecificosRepository outrosCursosEspecificosRepository;

        @Autowired
        protected RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository;

        @Autowired
        protected PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository;
        @Autowired
        protected LocalFuncionamentoEscolaRepository localFuncionamentoEscolaRepository;
        @Autowired
        protected EscolasComQualCompartilhaRepository escolasComQualCompartilhaRepository;
        @Autowired
        protected AbastecimentoAguaRespository abastecimentoAguaRespository;
        @Autowired
        private FonteEnergiaEletricaRepository fonteEnergiaEletricaRepository;
        @Autowired
        private EsgotamentoSanitarioRepository esgotamentoSanitarioRepository;
        @Autowired
        private DestinacaoLixoRepository destinacaoLixoRepository;
        @Autowired
        private TratamentoLixoRepository tratamentoLixoRepository;
        @Autowired
        private DependenciasFisicasRepository dependenciasFisicasRepository;
        @Autowired
        private RecursoPessoasDeficientesRepository recursoPessoasDeficientesRepository;
        @Autowired
        private EquipamentosExistentesUnidadeRepository equipamentosExistentesUnidadeRepository;
        @Autowired
        private QuantidadeEquipamentosProcessoApredizagemRepository quantidadeEquipamentosProcessoApredizagemRepository;
        @Autowired
        private AcessoInternetRepository acessoInternetRepository;
        @Autowired
        private EquipamentosUsadosAlunosParaAcessoInternetRepository equipamentosUsadosAlunosParaAcessoInternetRepository;
        @Autowired
        private RedeLocalInterligacaoComputadoresRepository redeLocalInterligacaoComputadoresRepository;
        @Autowired
        private TotalProfissionaisAtivosEscolaRepository totalProfissionaisAtivosEscolaRepository;
        @Autowired
        private InstrumentosMateriaisSocioCulturaisRepository instrumentosMateriaisSocioCulturaisRepository;
        @Autowired
        private IdiomaEnsinoRepository idiomaEnsinoRepository;
        @Autowired
        private LinguaIndigenaRepository linguaIndigenaRepository;
        @Autowired
        private ReservaVagasSistemaCotasRepository reservaVagasSistemaCotasRepository;
        @Autowired
        private OrgaosColegiadosFuncionamentoEscolaRepository orgaosColegiadosFuncionamentoEscolaRepository;
        @Autowired
        private QuantidadeComputadoresEmUsoAlunosRepository quantidadeComputadoresEmUsoAlunosRepository;

        @PostConstruct
        private void postConstruct() {
            for (TipoImportacaoEducacenso item : EnumSet.allOf(TipoImportacaoEducacenso.class)) {
                item.setPessoaRepository(pessoaRepository);
                item.setUnidadeEnsinoRepository(unidadeEnsinoRepository);
                item.setAreaConhecimentoRepository(areaConhecimentoRepository);
                item.setTurma(turmaRepository);
                item.setAreaPosGraduacao(areaPosGraduacaoRepository);
                item.setFormacaoComplementarPedagogicaProfessorRepository(formacaoComplementarPedagogicaProfessorRepository);
                item.setTipoDeficienciaEspectroAltasHabilidadesRepository(tipoDeficienciaEspectroAltasHabilidadesRepository);
                item.setOutrosCursosEspecificosRepository(outrosCursosEspecificosRepository);
                item.setRecursoAlunoParaAvaliacaoInepRepository(recursoAlunoParaAvaliacaoInepRepository);
                item.setPosGraduacaoConcluidaProfessorRepository(posGraduacaoConcluidaProfessorRepository);
                item.setLocalFuncionamentoEscolaRepository(localFuncionamentoEscolaRepository);
                item.setEscolasComQualCompartilhaRepository(escolasComQualCompartilhaRepository);
                item.setAbastecimentoAguaRespository(abastecimentoAguaRespository);
                item.setFonteEnergiaEletricaRepository(fonteEnergiaEletricaRepository);
                item.setEsgotamentoSanitarioRepository(esgotamentoSanitarioRepository);
                item.setDestinacaoLixo(destinacaoLixoRepository);
                item.setTratamentoLixoRepository(tratamentoLixoRepository);
                item.setDependenciasFisicasRepository(dependenciasFisicasRepository);
                item.setRecursoPessoasDeficientesRepository(recursoPessoasDeficientesRepository);
                item.setEquipamentosExistentesUnidadeRepository(equipamentosExistentesUnidadeRepository);
                item.setQuantidadeEquipamentosProcessoApredizagemRepository(quantidadeEquipamentosProcessoApredizagemRepository);
                item.setAcessoInternetRepository(acessoInternetRepository);
                item.setEquipamentosUsadosAlunosParaAcessoInternetRepository(equipamentosUsadosAlunosParaAcessoInternetRepository);
                item.setRedeLocalInterligacaoComputadoresRepository(redeLocalInterligacaoComputadoresRepository);
                item.setTotalProfissionaisAtivosEscolaRepository(totalProfissionaisAtivosEscolaRepository);
                item.setInstrumentosMateriaisSocioCulturaisRepository(instrumentosMateriaisSocioCulturaisRepository);
                item.setIdiomaEnsinoRepository(idiomaEnsinoRepository);
                item.setLinguaIndigenaRepository(linguaIndigenaRepository);
                item.setReservaVagasSistemaCotasRepository(reservaVagasSistemaCotasRepository);
                item.setOrgaosColegiadosFuncionamentoEscolaRepository(orgaosColegiadosFuncionamentoEscolaRepository);
                item.setQuantidadeComputadoresEmUsoAlunosRepository(quantidadeComputadoresEmUsoAlunosRepository);
            }
        }
    }

}


