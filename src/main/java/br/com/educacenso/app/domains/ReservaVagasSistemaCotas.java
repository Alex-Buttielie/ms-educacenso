package br.com.educacenso.app.domains;

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

@Table(name = "reserva_vagas_sistema_cotas")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaVagasSistemaCotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "auto_declaradado_negro_indigena")
    private Boolean autoDeclaradoPretoPardoOuIndigena;
    @Basic
    @Column(name = "condicao_renda")
    private Boolean condicaoDeRenda;
    @Basic
    @Column(name = "oriundo_escola_publica")
    private Boolean oriundoEscolaPublica;
    @Basic
    @Column(name = "pessoa_com_deficiencia")
    private Boolean pessoaComDeficiencia;
    @Basic
    @Column(name = "outros_grupos_nao_listados")
    private Boolean outrosGruposNaoListados;
    @Basic
    @Column(name = "is_reserva_vagas_cotas")
    private Boolean isReservaVagasCotas;
    @Basic
    @Column(name = "escola_possui_redes_comunicacao_institucional")
    private Boolean escolaPossuiRedesPComunicacaoInstitucional;
    @Basic
    @Column(name = "escola_compartilha_espacos_comunidade")
    private Boolean escolaCompartilhaEspacosComunidade;
    @Basic
    @Column(name = "espaco_usado_atividade_alunos")
    private Boolean espacoUsadoAtividadeAlunos;

}
