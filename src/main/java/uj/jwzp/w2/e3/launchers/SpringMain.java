package uj.jwzp.w2.e3.launchers;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.JOptCommandLinePropertySource;
import org.springframework.core.env.PropertySource;
import uj.jwzp.w2.e3.logic.CommandLineParser;
import uj.jwzp.w2.e3.logic.TransactionsGenerator;

import java.io.IOException;

@Slf4j
public class SpringMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("uj.jwzp.w2.e3");
        CommandLineParser commandLineParser = (CommandLineParser) ctx.getBean("commandLineParser");
        OptionSet options = commandLineParser.getParser().parse(args);

        PropertySource ps = new JOptCommandLinePropertySource(options);
        ctx.getEnvironment().getPropertySources().addFirst(ps);

        TransactionsGenerator generator = (TransactionsGenerator) ctx.getBean("transactionsGenerator");
        try {
            generator.generate();
        } catch (IOException e) {
            log.error("Failed to generate transactions.");
        }
    }
}
