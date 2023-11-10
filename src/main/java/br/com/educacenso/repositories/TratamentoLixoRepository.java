package br.com.educacenso.repositories;

import br.com.educacenso.app.domains.TratamentoLixo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TratamentoLixoRepository extends JpaRepository<TratamentoLixo, Long> {
}
