package br.com.educacenso.app.unidadeEnsino.repositorys;

import br.com.educacenso.app.unidadeEnsino.domains.AbastecimentoAgua;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AbastecimentoAguaRespository extends JpaRepository<AbastecimentoAgua, Long> {
}
