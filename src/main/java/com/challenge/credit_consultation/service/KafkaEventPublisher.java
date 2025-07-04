package com.challenge.credit_consultation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventPublisher.class);
    private static final String TOPIC_CONSULTA_CREDITO = "consulta-credito";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publicarConsultaPorNumeroNfse(String numeroNfse, int quantidadeEncontrada) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("tipoConsulta", "NUMERO_NFSE");
        evento.put("numeroNfse", numeroNfse);
        evento.put("quantidadeEncontrada", quantidadeEncontrada);
        evento.put("timestamp", LocalDateTime.now().toString());

        publicarEvento(evento);
    }

    public void publicarConsultaPorNumeroCredito(String numeroCredito, boolean encontrado) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("tipoConsulta", "NUMERO_CREDITO");
        evento.put("numeroCredito", numeroCredito);
        evento.put("encontrado", encontrado);
        evento.put("timestamp", LocalDateTime.now().toString());

        publicarEvento(evento);
    }

    private void publicarEvento(Map<String, Object> evento) {
        try {
            String eventoJson = objectMapper.writeValueAsString(evento);
            kafkaTemplate.send(TOPIC_CONSULTA_CREDITO, eventoJson);
            logger.info("Evento de consulta publicado: {}", eventoJson);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao serializar evento para JSON", e);
        }
    }

}
