//package com.example.bisneslogic.configuration;
//import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import org.springframework.core.convert.converter.Converter;
//
//public class StringToLocalDateConverter implements Converter<String, LocalDate> {
//    @Override
//    public LocalDate convert(String str) {
//        try {
//            return LocalDate.parse(str);
//        } catch (DateTimeParseException e) {
//            // handle the exception if the string cannot be parsed to LocalDate
//            return null;
//        }
//    }
//}