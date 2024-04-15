package com.example.labtestproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** В рантайме программа проверяет есть ли в нужной таблице актуальные курсы валют,
 * после работы, засыпает до начала следующего дня. */
@SpringBootApplication
public class LabTestProjectApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LabTestProjectApplication.class, args);
	}
}
