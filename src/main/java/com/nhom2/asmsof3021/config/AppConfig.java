package com.nhom2.asmsof3021.config;


import com.nhom2.asmsof3021.factory.ProductFactory;
import com.nhom2.asmsof3021.model.Orders;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.passay.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Configuration
public class AppConfig {
    @Bean
    public Map<Integer, ProductFactory> factoryMap() {
        Map<Integer, ProductFactory> factoryMap = new HashMap<>();

        // Thêm ánh xạ cho từng loại sản phẩm
        factoryMap.put(1, new ProductFactory.LaptopFactory());
        factoryMap.put(2, new ProductFactory.MonitorFactory());
        factoryMap.put(3,new ProductFactory.ComputerFactory());

        return factoryMap;
    }
    @Bean
    public RedirectStrategy redirectStrategy(){
        return new DefaultRedirectStrategy();
    }
    @Bean
    public PasswordValidator passwordValidator(){
        Properties props = new Properties();

        InputStream inputStream = getClass()

                .getClassLoader().getResourceAsStream("passay.properties");

        try {
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MessageResolver resolver = new PropertiesMessageResolver(props);
        PasswordValidator passwordValidator=new PasswordValidator(resolver,
                new LengthRule(8,16),
                new CharacterRule(EnglishCharacterData.UpperCase,1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );
        return passwordValidator;
    }
    @Bean("ORDERS_QUEUE")
    public ArrayDeque<Orders> ordersQueue(){

        return new ArrayDeque<>();
    }
}
