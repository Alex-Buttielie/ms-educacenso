package br.com.educacenso.repositories;

import br.com.educacenso.app.domains.LocalFuncionamentoEscola;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LocalFuncionamentoEscolaRepository extends JpaRepository<LocalFuncionamentoEscola, Long> {
}
