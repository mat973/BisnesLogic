package com.example.bisneslogic;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
public class BisnesLogicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BisnesLogicApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
//    @Bean
//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.findAndRegisterModules();
//        return new MappingJackson2HttpMessageConverter(mapper);
//    }
}
