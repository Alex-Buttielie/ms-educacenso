package br.com.educacenso.app.unidadeEnsino.repositorys;

import br.com.educacenso.app.unidadeEnsino.domains.QuantidadeComputadoresEmUsoAlunos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantidadeComputadoresEmUsoAlunosRepository extends JpaRepository<QuantidadeComputadoresEmUsoAlunos, Long> {
}
