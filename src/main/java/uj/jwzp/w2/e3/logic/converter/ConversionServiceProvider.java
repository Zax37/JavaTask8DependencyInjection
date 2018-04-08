package uj.jwzp.w2.e3.logic.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionServiceProvider {
    @Bean
    public ConversionService conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        ConversionService object = bean.getObject();
        return object;
    }

    private Set<Converter<?, ?>> getConverters() {
        Set<Converter<?, ?>> converters = new HashSet<Converter<?, ?>>();

        converters.add(new IntRangeConverter());
        converters.add(new DateRangeConverter());

        return converters;
    }
}