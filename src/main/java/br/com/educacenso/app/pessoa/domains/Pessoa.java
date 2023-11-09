package br.com.educacenso.app.pessoa.domains;
import br.com.educacenso.app.docente.constraints.LocalizacaoDiferenciadaResidencia;
import br.com.educacenso.app.docente.constraints.LocalizacaoZonaResidencia;
import br.com.educacenso.app.docente.domains.RecursoAlunoParaAvaliacaoInep;
import br.com.educacenso.app.docente.domains.TipoDeficienciaEspectroAltasHabilidades;
import br.com.educacenso.app.municipio.Municipio;
import br.com.educacenso.app.pessoa.constraints.CorRaca;
import br.com.educacenso.app.pessoa.constraints.Nacionalidade;
import br.com.educacenso.app.pessoa.constraints.Sexo;
import br.com.educacenso.app.pessoa.constraints.TipoFiliacao;
import br.com.educacenso.app.unidadeEnsino.domains.UnidadeEnsino;
import br.com.educacenso.contraints.Paises;
import br.com.educacenso.contraints.TipoRegistro;
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
import java.io.Serializable;
import java.util.Date;

@Table(name = "pessoa")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "identificacao_unica")
    private Long identificacaoUnica;
    @Basic
    @Column(name = "tipo_registro")
    private TipoRegistro tipoRegistro;
    @Basic
    @Column(name = "cpf")
    private String cpf;
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    @Basic
    @Column(name = "tipo_filiacao")
    private TipoFiliacao tipoFiliacao;
    @Basic
    @Column(name = "nome_mae")
    private String nomeMae;
    @Basic
    @Column(name = "nome_pai")
    private String nomePai;
    @Basic
    @Column(name = "sexo")
    private Sexo sexo;
    @Basic
    @Column(name = "cor_raca")
    private CorRaca corRaca;
    @Basic
    @Column(name = "nacionalidade")
    private Nacionalidade nacionalidade;
    @Basic
    @Column(name = "pais_nacionalidade")
    private Paises paisNacionalidade;
    @Basic
    @Column(name = "numero_matricula_certidao_nascimento")
    private String numeroMatriculaCertidaoNascimento;
    @Basic
    @Column(name = "pais_residencia")
    private Paises paisResidencia;
    @Basic
    @Column(name = "cep")
    private String cep;
    @Basic
    @Column(name = "localizacao_zona_residencia")
    private LocalizacaoZonaResidencia localizacaoZonaResidencia;
    @Basic
    @Column(name = "localizacao_diferenciada_residencia")
    private LocalizacaoDiferenciadaResidencia localizacaoDiferenciadaResidencia;
    @Basic
    @Column(name = "endereco_eletronico_email")
    private String enderecoEletronicoEmail;
    @OneToOne()
    @JoinColumn()
    @JsonFormat
    private UnidadeEnsino fkUni;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @JsonFormat
    private Municipio fkMunNasc;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private TipoDeficienciaEspectroAltasHabilidades tipoDeficienciaEspectroAltasHabilidades;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private RecursoAlunoParaAvaliacaoInep recursoAlunoParaAvaliacaoInep;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn()
    @JsonFormat
    private Municipio fkMunResid;

}
