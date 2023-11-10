package br.com.educacenso.repositories;

import br.com.educacenso.app.domains.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Turma findTurmaByCodigoInep(Long codigoInep);
}
