package br.com.educacenso.app;

import br.com.educacenso.repositories.AreaConhecimentoRepository;
import br.com.educacenso.repositories.AreaPosGraduacaoRepository;
import br.com.educacenso.repositories.FormacaoComplementarPedagogicaProfessorRepository;
import br.com.educacenso.repositories.OutrosCursosEspecificosRepository;
import br.com.educacenso.repositories.PessoaRepository;
import br.com.educacenso.repositories.PosGraduacaoConcluidaProfessorRepository;
import br.com.educacenso.repositories.RecursoAlunoParaAvaliacaoInepRepository;
import br.com.educacenso.repositories.TipoDeficienciaEspectroAltasHabilidadesRepository;
import br.com.educacenso.repositories.UnidadeEnsinoRepository;
import org.springframework.beans.factory.annotation.Autowired;


public  class GenericProfessorImportacao extends GenericPessoaImportacao {

    protected UnidadeEnsinoRepository unidadeEnsinoRepository;

    protected AreaConhecimentoRepository areaConhecimentoRepository;

    protected AreaPosGraduacaoRepository areaPosGraduacaoRepository;

    protected FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository;

    protected TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository;

    protected OutrosCursosEspecificosRepository outrosCursosEspecificosRepository;

    protected RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository;
    protected PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository;

    @Autowired
    public GenericProfessorImportacao(PessoaRepository pessoaRepository,
                                      UnidadeEnsinoRepository unidadeEnsinoRepository,
                                      AreaConhecimentoRepository areaConhecimentoRepository,
                                      AreaPosGraduacaoRepository areaPosGraduacaoRepository,
                                      FormacaoComplementarPedagogicaProfessorRepository formacaoComplementarPedagogicaProfessorRepository,
                                      TipoDeficienciaEspectroAltasHabilidadesRepository tipoDeficienciaEspectroAltasHabilidadesRepository,
                                      OutrosCursosEspecificosRepository outrosCursosEspecificosRepository,
                                      RecursoAlunoParaAvaliacaoInepRepository recursoAlunoParaAvaliacaoInepRepository,
                                      PosGraduacaoConcluidaProfessorRepository posGraduacaoConcluidaProfessorRepository) {
        super(pessoaRepository, unidadeEnsinoRepository);
        this.unidadeEnsinoRepository = unidadeEnsinoRepository;
        this.areaConhecimentoRepository = areaConhecimentoRepository;
        this.areaPosGraduacaoRepository = areaPosGraduacaoRepository;
        this.formacaoComplementarPedagogicaProfessorRepository = formacaoComplementarPedagogicaProfessorRepository;
        this.tipoDeficienciaEspectroAltasHabilidadesRepository = tipoDeficienciaEspectroAltasHabilidadesRepository;
        this.outrosCursosEspecificosRepository = outrosCursosEspecificosRepository;
        this.recursoAlunoParaAvaliacaoInepRepository = recursoAlunoParaAvaliacaoInepRepository;
        this.posGraduacaoConcluidaProfessorRepository = posGraduacaoConcluidaProfessorRepository;
    }

    public GenericProfessorImportacao() {

    }

}


