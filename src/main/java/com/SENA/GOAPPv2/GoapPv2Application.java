package com.SENA.GOAPPv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling // Habilita las tareas programadas
public class GoapPv2Application {
	public static void main(String[] args) {
		SpringApplication.run(GoapPv2Application.class, args);
	}

}
