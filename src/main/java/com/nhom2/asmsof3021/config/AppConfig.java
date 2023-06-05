package com.nhom2.asmsof3021.config;


import com.nhom2.asmsof3021.factory.ProductFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
}
