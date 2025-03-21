package br.com.educacenso.app.domains;

import br.com.educacenso.app.constraints.TipoMediacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "turma")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "codigo_inep")
    private Long codigoInep;
    @Basic
    @Column(name = "codigo_turma")
    private String codigoTurma;
    @Basic
    @Column(name = "nome")
    private String nome;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private Diretriz diretriz;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private Turno turno;
    @Basic
    @Column(name = "data_criacao")
    private Date dataCriacao;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private SalaAula salaAula;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino unidadeEnsino;
    @Basic
    @Column(name = "tipo_mediacao")
    private TipoMediacao tipoMediacao;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private HorarioFuncionamentoTurma horarioFuncionamento;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private DiasSemanaTurma diasSemana;
    @Basic
    @Column(name = "escolarizacao")
    private Boolean escolarizacao;
    @Basic
    @Column(name = "atividade_complementar")
    private Boolean atividadeComplementar;
    @Basic
    @Column(name = "atendimento_especializado")
    private Boolean atendimentoEspecializado;
    @Basic
    @Column(name = "formacao_geral_basica")
    private Boolean formacaoGeralBasica;
    @Basic
    @Column(name = "itinerario_informativo")
    private Boolean itinerarioInformativo;
    @Basic
    @Column(name = "nao_se_aplica")
    private Boolean naoSeAplica;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private TipoAtividadeComplementar tipoAtividadeComplementar;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private FormaOrganizacaoTurma formaOrganizacaoTurma;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private UnidadeCurricularTurma unidadeCurricularTurma;
}
