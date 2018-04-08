package uj.jwzp.w2.e3.launchers;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.JOptCommandLinePropertySource;
import org.springframework.core.env.PropertySource;
import uj.jwzp.w2.e3.logic.CommandLineParser;
import uj.jwzp.w2.e3.logic.TransactionsGenerator;

import java.io.IOException;

@Slf4j
public class SpringMain {
    public static void main(String[] args) {
        OptionParser parser = CommandLineParser.getParser();
        OptionSet options = parser.parse(args);

        PropertySource ps = new JOptCommandLinePropertySource(options);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(ps);
        ctx.scan("uj.jwzp.w2.e3");
        ctx.refresh();

        TransactionsGenerator generator = (TransactionsGenerator) ctx.getBean("transactionsGenerator");
        try {
            generator.generate();
        } catch (IOException e) {
            log.error("Failed to generate transactions.");
        }
    }
}
