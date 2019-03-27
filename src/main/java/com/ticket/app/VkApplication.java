package com.ticket.app;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class VkApplication {
    public static void main(String[] args) throws JSONException, IOException {
		SpringApplication.run(VkApplication.class, args);
	}
}
