package com.challenge.credit_consultation.common.presenter;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreditoPresenter {
    private String numeroCredito;
    private String numeroNfse;
    private LocalDate dataConstituicao;
    private BigDecimal valorIssqn;
    private String tipoCredito;
    private String simplesNacional;
    private BigDecimal aliquota;
    private BigDecimal valorFaturado;
    private BigDecimal valorDeducao;
    private BigDecimal baseCalculo;
}
