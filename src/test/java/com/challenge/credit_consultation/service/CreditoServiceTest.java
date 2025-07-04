package com.challenge.credit_consultation.service;

import com.challenge.credit_consultation.common.entity.Credito;
import com.challenge.credit_consultation.common.presenter.CreditoPresenter;
import com.challenge.credit_consultation.data.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @InjectMocks
    private CreditoService creditoService;

    private Credito credito;

    @BeforeEach
    void setUp() {
        credito = new Credito();
        credito.setNumeroCredito("12345");
        credito.setNumeroNfse("NFSE123");
        credito.setDataConstituicao(LocalDate.of(2024, 1, 15));
        credito.setValorIssqn(BigDecimal.valueOf(100.50));
        credito.setTipoCredito("TIPO_A");
        credito.setSimplesNacional(true);
        credito.setAliquota(BigDecimal.valueOf(5.0));
        credito.setValorFaturado(BigDecimal.valueOf(1000.0));
        credito.setValorDeducao(BigDecimal.valueOf(50.0));
        credito.setBaseCalculo(BigDecimal.valueOf(950.0));
    }

    @Test
    void buscarCreditosPorNumeroNfse_DeveRetornarListaDeCreditos() {
        // Arrange
        String numeroNfse = "NFSE123";
        List<Credito> creditos = Collections.singletonList(credito);
        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(creditos);

        // Act
        List<CreditoPresenter> resultado = creditoService.buscarCreditosPorNumeroNfse(numeroNfse);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        CreditoPresenter presenter = resultado.get(0);
        assertEquals("12345", presenter.getNumeroCredito());
        assertEquals("NFSE123", presenter.getNumeroNfse());
        assertEquals(LocalDate.of(2024, 1, 15), presenter.getDataConstituicao());
        assertEquals(BigDecimal.valueOf(100.50), presenter.getValorIssqn());
        assertEquals("TIPO_A", presenter.getTipoCredito());
        assertEquals("Sim", presenter.getSimplesNacional());
        assertEquals(BigDecimal.valueOf(5.0), presenter.getAliquota());
        assertEquals(BigDecimal.valueOf(1000.0), presenter.getValorFaturado());
        assertEquals(BigDecimal.valueOf(50.0), presenter.getValorDeducao());
        assertEquals(BigDecimal.valueOf(950.0), presenter.getBaseCalculo());

        verify(creditoRepository, times(1)).findByNumeroNfse(numeroNfse);
    }

    @Test
    void buscarCreditosPorNumeroNfse_DeveRetornarListaVazia() {
        // Arrange
        String numeroNfse = "NFSE999";
        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(List.of());

        // Act
        List<CreditoPresenter> resultado = creditoService.buscarCreditosPorNumeroNfse(numeroNfse);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(creditoRepository, times(1)).findByNumeroNfse(numeroNfse);
    }

    @Test
    void buscarCreditoPorNumeroCredito_DeveRetornarCredito() {
        // Arrange
        String numeroCredito = "12345";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(credito));

        // Act
        Optional<CreditoPresenter> resultado = creditoService.buscarCreditoPorNumeroCredito(numeroCredito);

        // Assert
        assertTrue(resultado.isPresent());
        CreditoPresenter presenter = resultado.get();
        assertEquals("12345", presenter.getNumeroCredito());
        assertEquals("NFSE123", presenter.getNumeroNfse());
        assertEquals("Sim", presenter.getSimplesNacional());

        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
    }

    @Test
    void buscarCreditoPorNumeroCredito_DeveRetornarEmpty() {
        // Arrange
        String numeroCredito = "99999";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        // Act
        Optional<CreditoPresenter> resultado = creditoService.buscarCreditoPorNumeroCredito(numeroCredito);

        // Assert
        assertFalse(resultado.isPresent());
        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
    }

    @Test
    void convertToPresenter_ComSimplesNacionalFalse_DeveRetornarNao() {
        // Arrange
        credito.setSimplesNacional(false);
        when(creditoRepository.findByNumeroCredito("12345")).thenReturn(Optional.of(credito));

        // Act
        Optional<CreditoPresenter> resultado = creditoService.buscarCreditoPorNumeroCredito("12345");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Não", resultado.get().getSimplesNacional());
    }
}