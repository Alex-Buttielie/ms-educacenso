package br.com.educacenso.repositories;

import br.com.educacenso.app.domains.AcessoInternet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcessoInternetRepository extends JpaRepository<AcessoInternet, Long> {
}
