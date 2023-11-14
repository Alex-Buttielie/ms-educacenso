package br.com.educacenso.repositories;

import br.com.educacenso.app.domains.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findPessoaByCpf(String cpf);
    Optional<Pessoa> findPessoaByNome(String nome);

}
