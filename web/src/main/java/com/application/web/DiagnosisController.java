package com.application.web;

import com.application.data.Diagnosis;
import com.application.data.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosisController {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @GetMapping("/diagnosisList")
    public Page<Diagnosis> getAll(Pageable pageable) {
        return diagnosisRepository.findAll(pageable);
    }
}
