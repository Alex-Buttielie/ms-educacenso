package br.com.educacenso.app.repositories;

import br.com.educacenso.app.domains.AbastecimentoAgua;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AbastecimentoAguaRespository extends JpaRepository<AbastecimentoAgua, Long> {
}
