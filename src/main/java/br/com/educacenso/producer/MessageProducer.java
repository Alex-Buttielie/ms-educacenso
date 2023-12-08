package br.com.educacenso.producer;

import br.com.educacenso.app.domains.Pessoa;
import br.com.educacenso.app.domains.UnidadeEnsino;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void enviarUnidade(UnidadeEnsino entity) {
        enviarMensagem("unidade", entity);
    }


    public void enviarPessoa(Pessoa entity) {
        enviarMensagem("pessoa", entity);
    }

    @RabbitListener
    private void enviarMensagem(String tipo, Object entity) {
        try {
            String serializedMessage = objectMapper.writeValueAsString(entity);
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("tipo", tipo);
            messageMap.put("dados", serializedMessage);
            amqpTemplate.convertAndSend(exchange, routingKey, messageMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
