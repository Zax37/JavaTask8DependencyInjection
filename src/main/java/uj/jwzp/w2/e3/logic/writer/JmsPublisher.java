package uj.jwzp.w2.e3.logic.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import uj.jwzp.w2.e3.model.Transaction;

import java.util.Optional;

@Component
public class JmsPublisher {
    private JmsTemplate jmsTemplate;
    private String destination;
    private boolean isBrokerSupplied = true;

    @Autowired
    public JmsPublisher(Optional<JmsTemplate> jmsTemplate, Optional<String> destination) {
        if (jmsTemplate.isPresent()) {
            this.jmsTemplate = jmsTemplate.get();
            this.destination = destination.get();
        } else {
            this.isBrokerSupplied = false;
        }
    }

    public void send(Transaction transaction){
        if (isBrokerSupplied) {
            jmsTemplate.convertAndSend(destination, transaction);
        }
    }
}
