package br.com.educacenso.app.domains;

import br.com.educacenso.app.constraints.TipoEnsinoMedioCursado;
import br.com.educacenso.app.constraints.MaiorNivelEscolaridadeConcluido;
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

@Table(name = "professor")
@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private Pessoa pessoa;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private OutrosCursosEspecificos outrosCursosEspecificos;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private FormacaoComplementarPedagogicaProfessor formacaoComplementarPedagogicaProfessor;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private PosGraduacaoConcluidaProfessor posGraduacaoConcluidaProfessor;
    @Basic
    @Column(name = "nao_tem_pos_graduacao_concluida")
    private Boolean naoTemPosGraduacaoConcluida;
    @Basic
    @Column(name = "tipo_ensino_medio_cursado")
    private TipoEnsinoMedioCursado tipoEnsinoMedioCursado;
    @Basic
    @Column(name = "maior_nivel_escolaridade_concluido")
    private MaiorNivelEscolaridadeConcluido maiorNivelEscolaridadeConcluido;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private TipoDeficienciaEspectroAltasHabilidades tipoDeficienciaEspectroAltasHabilidades;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private RecursoAlunoParaAvaliacaoInep recursoAlunoParaAvaliacaoInep;
}
