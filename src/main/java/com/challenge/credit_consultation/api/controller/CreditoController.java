package com.challenge.credit_consultation.api.controller;


import com.challenge.credit_consultation.common.presenter.CreditoPresenter;
import com.challenge.credit_consultation.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("creditos")
public class CreditoController {

    private final CreditoService creditoService;

    @Autowired
    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoPresenter>> buscarCreditos(@PathVariable String numeroNfse) {
        List<CreditoPresenter> creditos = creditoService.buscarCreditosPorNumeroNfse(numeroNfse);
        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoPresenter> buscarCreditoPorNumero(@PathVariable String numeroCredito) {
        Optional<CreditoPresenter> credito = creditoService.buscarCreditoPorNumeroCredito(numeroCredito);
        return credito.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
