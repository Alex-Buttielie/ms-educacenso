package br.com.educacenso.config;

import br.com.educacenso.app.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Autowired
    private PessoaRepository repository;

}
