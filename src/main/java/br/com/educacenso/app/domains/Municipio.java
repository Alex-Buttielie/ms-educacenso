package br.com.educacenso.app.domains;


import lombok.AllArgsConstructor;
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

@Table(name = "municipio")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Municipio {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Id
    @Basic
    @Column(name="codigo_mec")
    private Long codigoMec;
    @Basic
    @Column(name="nome")
    private String nome;
}
