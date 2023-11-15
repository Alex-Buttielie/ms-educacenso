package br.com.educacenso.app.repositories;

import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findProfessorByPessoa(Pessoa pessoa);
}
