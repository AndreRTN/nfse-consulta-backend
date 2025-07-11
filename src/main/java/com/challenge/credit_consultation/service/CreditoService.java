package com.challenge.credit_consultation.service;

import com.challenge.credit_consultation.common.entity.Credito;
import com.challenge.credit_consultation.common.presenter.CreditoPresenter;
import com.challenge.credit_consultation.data.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final KafkaEventPublisher kafkaEventPublisher;

    @Autowired
    public CreditoService(CreditoRepository creditoRepository, KafkaEventPublisher kafkaEventPublisher) {
        this.creditoRepository = creditoRepository;
        this.kafkaEventPublisher = kafkaEventPublisher;
    }

    public List<CreditoPresenter> buscarCreditosPorNumeroNfse(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);

        List<CreditoPresenter> resultado = creditos.stream()
                .map(this::convertToPresenter)
                .toList();

        kafkaEventPublisher.publicarConsultaPorNumeroNfse(numeroNfse, resultado.size());
        return creditos.stream()
                .map(this::convertToPresenter)
                .collect(Collectors.toList());
    }

    public Optional<CreditoPresenter> buscarCreditoPorNumeroCredito(String numeroCredito) {
        Optional<Credito> credito = creditoRepository.findByNumeroCredito(numeroCredito);
        Optional<CreditoPresenter> resultado = credito.map(this::convertToPresenter);

        kafkaEventPublisher.publicarConsultaPorNumeroCredito(numeroCredito, resultado.isPresent());
        return resultado;
    }

    private CreditoPresenter convertToPresenter(Credito credito) {
        CreditoPresenter presenter = new CreditoPresenter();
        presenter.setNumeroCredito(credito.getNumeroCredito());
        presenter.setNumeroNfse(credito.getNumeroNfse());
        presenter.setDataConstituicao(credito.getDataConstituicao());
        presenter.setValorIssqn(credito.getValorIssqn());
        presenter.setTipoCredito(credito.getTipoCredito());
        presenter.setSimplesNacional(credito.isSimplesNacional() ? "Sim" : "Não");
        presenter.setAliquota(credito.getAliquota());
        presenter.setValorFaturado(credito.getValorFaturado());
        presenter.setValorDeducao(credito.getValorDeducao());
        presenter.setBaseCalculo(credito.getBaseCalculo());
        return presenter;
    }
}
