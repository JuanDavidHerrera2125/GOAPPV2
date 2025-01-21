package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.Entity.Code;
import com.SENA.GOAPPv2.IService.CodeIService;
import com.SENA.GOAPPv2.Service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/codes")
public class CodeController {

    @Autowired
    private CodeIService codeService;

    @GetMapping("/today")
    public ResponseEntity<Code> getTodayCode() {
        return codeService.findByGenerationDate(LocalDate.now())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


