package com.challenge.credit_consultation.controller;

import com.challenge.credit_consultation.api.controller.CreditoController;
import com.challenge.credit_consultation.common.presenter.CreditoPresenter;
import com.challenge.credit_consultation.service.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoControllerTest {

    @Mock
    private CreditoService creditoService;

    @InjectMocks
    private CreditoController creditoController;

    private CreditoPresenter creditoPresenter;

    @BeforeEach
    void setUp() {
        creditoPresenter = new CreditoPresenter();
        creditoPresenter.setNumeroCredito("12345");
        creditoPresenter.setNumeroNfse("NFSE123");
        creditoPresenter.setDataConstituicao(LocalDate.of(2024, 1, 15));
        creditoPresenter.setValorIssqn(BigDecimal.valueOf(100.50));
        creditoPresenter.setTipoCredito("TIPO_A");
        creditoPresenter.setSimplesNacional("Sim");
        creditoPresenter.setAliquota(BigDecimal.valueOf(5.0));
        creditoPresenter.setValorFaturado(BigDecimal.valueOf(1000.0));
        creditoPresenter.setValorDeducao(BigDecimal.valueOf(50.0));
        creditoPresenter.setBaseCalculo(BigDecimal.valueOf(950.0));
    }

    @Test
    void buscarCreditos_DeveRetornarListaDeCreditos() {
        // Arrange
        String numeroNfse = "NFSE123";
        List<CreditoPresenter> creditos = Collections.singletonList(creditoPresenter);
        when(creditoService.buscarCreditosPorNumeroNfse(numeroNfse)).thenReturn(creditos);

        // Act
        ResponseEntity<List<CreditoPresenter>> response = creditoController.buscarCreditos(numeroNfse);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("12345", response.getBody().get(0).getNumeroCredito());
        assertEquals("NFSE123", response.getBody().get(0).getNumeroNfse());

        verify(creditoService, times(1)).buscarCreditosPorNumeroNfse(numeroNfse);
    }

    @Test
    void buscarCreditos_DeveRetornarListaVazia() {
        // Arrange
        String numeroNfse = "NFSE999";
        when(creditoService.buscarCreditosPorNumeroNfse(numeroNfse)).thenReturn(List.of());

        // Act
        ResponseEntity<List<CreditoPresenter>> response = creditoController.buscarCreditos(numeroNfse);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(creditoService, times(1)).buscarCreditosPorNumeroNfse(numeroNfse);
    }

    @Test
    void buscarCreditoPorNumero_DeveRetornarCredito() {
        // Arrange
        String numeroCredito = "12345";
        when(creditoService.buscarCreditoPorNumeroCredito(numeroCredito)).thenReturn(Optional.of(creditoPresenter));

        // Act
        ResponseEntity<CreditoPresenter> response = creditoController.buscarCreditoPorNumero(numeroCredito);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("12345", response.getBody().getNumeroCredito());
        assertEquals("NFSE123", response.getBody().getNumeroNfse());

        verify(creditoService, times(1)).buscarCreditoPorNumeroCredito(numeroCredito);
    }

    @Test
    void buscarCreditoPorNumero_DeveRetornarNotFound() {
        // Arrange
        String numeroCredito = "99999";
        when(creditoService.buscarCreditoPorNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<CreditoPresenter> response = creditoController.buscarCreditoPorNumero(numeroCredito);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(creditoService, times(1)).buscarCreditoPorNumeroCredito(numeroCredito);
    }
}