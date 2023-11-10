package br.com.educacenso.app.domains;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "escolas_com_qual_compartilha")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EscolasComQualCompartilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino codigoEscolaComQualCompartilha1;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino codigoEscolaComQualCompartilha2;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino codigoEscolaComQualCompartilha3;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino codigoEscolaComQualCompartilha4;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino codigoEscolaComQualCompartilha5;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino codigoEscolaComQualCompartilha6;
    @Basic
    @Column(name = "fornece_agua_potavel_para_consumo")
    private Boolean forneceAguaPotavelParaConsumo;

}
