package br.com.educacenso.repositories;

import br.com.educacenso.app.domains.EsgotamentoSanitario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EsgotamentoSanitarioRepository extends JpaRepository<EsgotamentoSanitario, Long> {
}
