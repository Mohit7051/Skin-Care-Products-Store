package com.itvedant.skincareproducts_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@ComponentScan(basePackages = "com.itvedant.gamestore")
@EnableConfigurationProperties({SkinCareProductsStoreStorageProperties.class})
public class SkinCareProductsStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkinCareProductsStoreApplication.class, args);
	}

}

