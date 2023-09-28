package br.com.educacenso.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImportacaoEducacensoService {
    void importarEducacenso(MultipartFile file) throws IOException;
}
