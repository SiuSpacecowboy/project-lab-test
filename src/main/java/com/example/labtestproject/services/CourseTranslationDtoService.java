package com.example.labtestproject.services;

import com.example.labtestproject.dto.CourseTranslationDto;
import com.example.labtestproject.repositories.CourseTranslationDtoRepository;
import com.example.labtestproject.subClasses.TwelveDataRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/** Класс для сохранения в таблицу и обновления курса валют. **/
@Service
public class CourseTranslationDtoService {

    private final CourseTranslationDtoRepository repository;
    private final WebClientService webClientService;

    @Autowired
    public CourseTranslationDtoService(CourseTranslationDtoRepository repository,
                                       WebClientService webClientService) {
        this.repository = repository;
        this.webClientService = webClientService;
    }

    @Scheduled(cron = "5 * * * * *")
    public void updateOrSaveCourseByTimer() {
        if (getAllCourses().isEmpty()) {
            saveCourse();
        } else {
            updateCourse();
        }
    }

    public void updateCourse() {
        List<TwelveDataRequestResult> resultList = webClientService.getTwelveDataRequestResult();
        LocalDate dateCourse = LocalDate.parse(resultList.get(0).getValues().get(0).getDatetime());
        double rubCourse = Double.parseDouble(resultList.get(0).getValues().get(0).getClose());
        double kztCourse = Double.parseDouble(resultList.get(1).getValues().get(0).getClose());
        repository.updateCourseDateAndCourseUsdRubAndCourseUsdKztById(dateCourse, rubCourse, kztCourse, 1);
        System.out.println("=============================================================================");
    }

    public void saveCourse() {
        List<TwelveDataRequestResult> resultList = webClientService.getTwelveDataRequestResult();
        LocalDate dateCourse = LocalDate.parse(resultList.get(0).getValues().get(0).getDatetime());
        double rubCourse = Double.parseDouble(resultList.get(0).getValues().get(0).getClose());
        double kztCourse = Double.parseDouble(resultList.get(1).getValues().get(0).getClose());
        CourseTranslationDto dto = new CourseTranslationDto(dateCourse, rubCourse, kztCourse);
        repository.save(dto);
        System.out.println("=============================================================================");
    }

    public List<CourseTranslationDto> getAllCourses() {
        return repository.findAll();
    }

}
