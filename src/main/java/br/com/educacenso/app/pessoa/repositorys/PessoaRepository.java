package br.com.educacenso.app.pessoa.repositorys;

import br.com.educacenso.app.pessoa.domains.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Supplier;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findPessoaByCpf(String cpf);
    Optional<Pessoa> findPessoaByNome(String nome);
}
