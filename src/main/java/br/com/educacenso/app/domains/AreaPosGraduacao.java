package br.com.educacenso.app.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "area_pos_graduacao")
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaPosGraduacao {

    @Id
    @Basic
    @Column(name="codigo")
    private Long codigo;
    @Basic
    @Column(name="nome")
    private String nome;


}
