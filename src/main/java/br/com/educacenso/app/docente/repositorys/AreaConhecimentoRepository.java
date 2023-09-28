package br.com.educacenso.app.docente.repositorys;

import br.com.educacenso.app.docente.domains.AreaConhecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaConhecimentoRepository extends JpaRepository<AreaConhecimento, Long> {
}
