package com.ticket.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class VkApplication {
    public static void main(String[] args) {
		SpringApplication.run(VkApplication.class, args);
		System.out.println(((4 * (1 - (10/100))) * 1.1));
		System.out.println(1 - 10/100);
		System.out.println(4 *  (1 - 10/100));
	}
}
