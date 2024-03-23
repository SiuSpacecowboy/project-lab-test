package com.example.labtestproject;

import com.example.labtestproject.config.ApplicationContextProviderConfig;
import com.example.labtestproject.services.CourseTranslationDtoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/** В рантайме программа проверяет есть ли в нужной таблице актуальные курсы валют,
 * после работы, засыпает до начала следующего дня. */
@SpringBootApplication
public class LabTestProjectApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LabTestProjectApplication.class, args);
		ApplicationContext context = ApplicationContextProviderConfig.getApplicationContext();
		CourseTranslationDtoService service = context.getBean("courseTranslationDtoService", CourseTranslationDtoService.class);
		if (service.getAllCourses().isEmpty()) {
			service.saveCourse();
		}
		while (true) {
			service.updateCourse();
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime nextDay = LocalDateTime.now().toLocalDate().plusDays(1).atStartOfDay();
			long millisToNewDay = ChronoUnit.MILLIS.between(now, nextDay);
			Thread.sleep(millisToNewDay);
		}
	}
}
