package br.com.educacenso.resources;

import br.com.educacenso.app.services.ImportacaoEducacensoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping(value = "/importacao-educacenso")
public class ImportacaoEducacensoResource {

    @Autowired
    private ImportacaoEducacensoService service;

    private ImportacaoEducacensoService getService () {
        return this.service;
    }

    @ApiOperation(value = "Realiza a importação do educacenso e suas respectivas tabelas")
    @PostMapping("importar")
    public ResponseEntity importar(@RequestParam(value="educacenso") MultipartFile educacenso) throws IOException {
        getService().importarEducacenso(educacenso);
        return ResponseEntity.ok().build();
    }
}
