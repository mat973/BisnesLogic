//package com.example.bisneslogic.configuration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
//import org.springframework.format.support.FormattingConversionServiceFactoryBean;
//
//import java.util.Collections;
//
//@Configuration
//public class AppConfig {
//    @Bean
//    public FormattingConversionServiceFactoryBean conversionService() {
//        FormattingConversionServiceFactoryBean factory = new FormattingConversionServiceFactoryBean();
//        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
//        registrar.setUseIsoFormat(true);
//        registrar.registerFormatters(factory);
//        factory.setConverters(Collections.singleton(new StringToLocalDateConverter()));
//        return factory;
//    }
//}