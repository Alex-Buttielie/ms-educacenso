package br.com.educacenso.app.repositories;

import br.com.educacenso.app.domains.DestinacaoLixo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DestinacaoLixoRepository extends JpaRepository<DestinacaoLixo, Long> {
}
