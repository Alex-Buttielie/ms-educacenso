package br.com.educacenso.app.repositories;

import br.com.educacenso.app.domains.QuantidadeComputadoresEmUsoAlunos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantidadeComputadoresEmUsoAlunosRepository extends JpaRepository<QuantidadeComputadoresEmUsoAlunos, Long> {
}
