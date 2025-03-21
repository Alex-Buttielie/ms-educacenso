package br.com.educacenso.app.domains;

import br.com.educacenso.app.constraints.FormaOcupacaoPredio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "local_funcionamento_escola")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalFuncionamentoEscola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "predio_escolar")
    private Boolean predioEscolar;
    @Basic
    @Column(name = "salas_em_outra_escola")
    private Boolean salasEmOutraEscola;
    @Basic
    @Column(name = "galpao_racho_paiol_barracao")
    private Boolean galpaoRanchoPaiolBarracao;
    @Basic
    @Column(name = "unidade_atendimento_socioeducativa")
    private Boolean unidadeAtendimentoSocioeducativa;
    @Basic
    @Column(name = "unidade_prisional")
    private Boolean unidadePrisional;
    @Basic
    @Column(name = "outros")
    private Boolean outros;
    @Basic
    @Column(name = "formas_ocupacao_predio")
    private FormaOcupacaoPredio formaOcupacaoPredio;
    @Basic
    @Column(name = "predio_escolar_compartilhado_com_outra_escola")
    private Boolean predioEscolarCompartilhadoComOutraEscola;

}
