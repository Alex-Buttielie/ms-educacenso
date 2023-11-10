package br.com.educacenso.app.domains;

import br.com.educacenso.app.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.constraints.DependenciaAdministrativa;
import br.com.educacenso.app.constraints.SituacaoFuncionamento;
import br.com.educacenso.contraints.TipoRegistro;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "unidade_ensino")
@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicUpdate
public class UnidadeEnsino {

    @Id
    @Basic
    @Column(name="codigo_inep")
    private Long codigoInep;
    @Basic
    @Column(name="nome")
    private String nome;
    @Basic
    @Column(name="tipo_registro")
    private TipoRegistro tipoDeRegistro;
    @Basic
    @Column(name="situacao_funcionamento")
    private SituacaoFuncionamento situacaoFuncionamento;
    @Basic
    @Column(name="data_incio_ano_letivo")
    private Date dataInicioAnoLetivo;
    @Basic
    @Column(name="data_termino_ano_letivo")
    private Date dataDeTerminoDoAnoLetivo;
    @Basic
    @Column(name="telefone")
    private Integer telefone;
    @Basic
    @Column(name="outro_telefone_contato")
    private Integer outroTelefoneContato;
    @Basic
    @Column(name="endereco_eletronico_email_escola")
    private String enderecoEletronicoEmailEscola;
    @Basic
    @Column(name="codigo_orgao_regional_ensino")
    private String codigoDoOrgaoRegionalDeEnsino;
    @Basic
    @Column(name="localizacao_zona_escola")
    private LocalizacaoZonaResidencia localizacaoZonaEscola;
    @Basic
    @Column(name="localizacao_diferenciada_escola")
    private LocalizacaoDiferenciadaResidencia localizacaoDiferenciadaEscola;
    @Basic
    @Column(name="dependencia_administrativa")
    private DependenciaAdministrativa dependenciaAdministrativa;
    @Basic
    @Column(name="secretaria_educacao")
    private Boolean secretariaEducacao;
    @Basic
    @Column(name="ssecretaria_seguranca_publica")
    private Boolean secretariaSegurancaPublica;
    @Basic
    @Column(name="secretaria_saude")
    private Boolean secretariaSaude;
    @Basic
    @Column(name="outro_orgao_administracao_publica")
    private Boolean outroOrgaoAdministracaoPublica;
    @Basic
    @Column(name="secretaria_estadual")
    private Boolean secretariaEstadual;
    @Basic
    @Column(name="secretaria_municipal")
    private Boolean secretariaMunicipal;
    @Basic
    @Column(name="nao_possui_parceria_convenio")
    private Boolean naoPossuiParceriaConvenio;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private EsferaAdministrativa esferaAdministrativa;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private EnderecoUnidade enderecoUnidade;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private MantenedoraEscolaPrivada MantenedoraEscolaPrivada;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private FormasContratacao formasContratacao;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private NumeroMatriculasAtendidasParceriaConvenio numeroMatriculasAtendidasParceriaConvenio;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private LocalFuncionamentoEscola localFuncionamentoEscola;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private EscolasComQualCompartilha escolasComQualCompartilha;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "abastecimento_agua_id", referencedColumnName = "id",  nullable = false, unique = false)
    @JsonFormat
    private AbastecimentoAgua abastecimentoAgua;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private FonteEnergiaEletrica fonteEnergiaEletrica;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private EsgotamentoSanitario esgotamentoSanitario;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private DestinacaoLixo destinacaoLixo;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private TratamentoLixo tratamentoLixo;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private DependenciasFisicas dependenciasFisicas;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private RecursoPessoasDeficientes recursoPessoasDeficientes;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private EquipamentosExistentesUnidade equipamentosExistentesUnidade;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private QuantidadeEquipamentosProcessoAprendizagem quantidadeEquipamentosProcessoAprendizagem;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private QuantidadeComputadoresEmUsoAlunos quantidadeComputadoresEmUsoAlunos;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private AcessoInternet acessoInternet;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private EquipamentosUsadosAlunosAcessoInternet equipamentosUsadosAlunosAcessoInternet;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private RedeLocalInterligacaoComputadores redeLocalInterligacaoComputadores;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private TotalProfissionaisAtivosEscola totalProfissionaisAtivosEscola;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private InstrumentosMateriaisSocioCulturais instrumentosMateriaisSocioCulturais;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private IdiomaEnsino idiomaEnsino;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private ReservaVagasSistemaCotas reservaVagasSistemaCotas;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private OrgaosColegiadosFuncionamentoEscola orgaosColegiadosFuncionamentoEscola;

}
