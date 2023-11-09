package br.com.educacenso.app.turma.repositorys;

import br.com.educacenso.app.turma.domains.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Turma findTurmaByCodigoInep(Long codigoInep);
}
