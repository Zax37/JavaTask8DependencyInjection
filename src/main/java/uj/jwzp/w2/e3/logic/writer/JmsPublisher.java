package uj.jwzp.w2.e3.logic.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import uj.jwzp.w2.e3.model.Transaction;

@Component
public class JmsPublisher {
    private JmsTemplate jmsTemplate;
    private String destination;

    @Autowired
    public JmsPublisher(JmsTemplate jmsTemplate, String destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    public void send(Transaction transaction){
        jmsTemplate.convertAndSend(destination, transaction);
    }
}
