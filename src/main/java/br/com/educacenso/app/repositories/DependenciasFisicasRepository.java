package br.com.educacenso.app.repositories;

import br.com.educacenso.app.domains.DependenciasFisicas;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DependenciasFisicasRepository extends JpaRepository<DependenciasFisicas, Long> {
}
