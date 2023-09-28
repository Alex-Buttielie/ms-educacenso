package br.com.educacenso.app.unidadeEnsino.repositorys;

import br.com.educacenso.app.unidadeEnsino.domains.LocalFuncionamentoEscola;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LocalFuncionamentoEscolaRepository extends JpaRepository<LocalFuncionamentoEscola, Long> {
}
