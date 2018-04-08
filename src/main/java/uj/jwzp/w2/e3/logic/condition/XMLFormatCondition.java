package uj.jwzp.w2.e3.logic.condition;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class XMLFormatCondition implements org.springframework.context.annotation.Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("format").equals("xml");
    }
}
