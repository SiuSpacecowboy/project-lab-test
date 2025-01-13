package com.example.labtestproject.services;

import com.example.labtestproject.entity.CourseTranslationEntity;
import com.example.labtestproject.repositories.CourseMongoRepository;
import com.example.labtestproject.repositories.CourseTranslationDtoRepository;
import com.example.labtestproject.subClasses.TwelveDataRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/** Класс для сохранения в таблицу и обновления курса валют. **/
@Service
public class CourseTranslationService {

    private final CourseTranslationDtoRepository repository;
    private final CourseMongoRepository rep;
    private final WebClientService webClientService;

    @Autowired
    public CourseTranslationService(CourseTranslationDtoRepository repository,
                                    CourseMongoRepository rep,
                                    WebClientService webClientService) {
        this.repository = repository;
        this.rep = rep;
        this.webClientService = webClientService;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void updateOrSaveCourseByTimer() {
        List<TwelveDataRequestResult> resultList = webClientService.getTwelveDataRequestResult();
        if (getAllCourses().isEmpty()) {
            saveCourse(resultList);
            //saveMongoCourse(resultList);
        } else {
            updateCourse(resultList);
            //updateMongoCourse(resultList);
        }
    }

    public void updateCourse(List<TwelveDataRequestResult> resultList) {
        LocalDate dateCourse = LocalDate.parse(resultList.get(0).getValues().get(0).getDatetime());
        double rubCourse = Double.parseDouble(resultList.get(0).getValues().get(0).getClose());
        double kztCourse = Double.parseDouble(resultList.get(1).getValues().get(0).getClose());
        repository.updateCourseDateAndCourseUsdRubAndCourseUsdKztById(dateCourse, rubCourse, kztCourse, 1);
    }

    public void saveCourse(List<TwelveDataRequestResult> resultList) {
        LocalDate dateCourse = LocalDate.parse(resultList.get(0).getValues().get(0).getDatetime());
        double rubCourse = Double.parseDouble(resultList.get(0).getValues().get(0).getClose());
        double kztCourse = Double.parseDouble(resultList.get(1).getValues().get(0).getClose());
        CourseTranslationEntity dto = new CourseTranslationEntity(dateCourse, rubCourse, kztCourse);
        repository.save(dto);
    }

    /** Альтернативное хранение данных в MongoDb*/

/*    public void saveMongoCourse(List<TwelveDataRequestResult> resultList) {
        LocalDate dateCourse = LocalDate.parse(resultList.get(0).getValues().get(0).getDatetime());
        double rubCourse = Double.parseDouble(resultList.get(0).getValues().get(0).getClose());
        double kztCourse = Double.parseDouble(resultList.get(1).getValues().get(0).getClose());
        rep.insert(new CourseMongoDto(1, dateCourse, rubCourse, kztCourse*//*, 1*//*));
    }

    public void updateMongoCourse(List<TwelveDataRequestResult> resultList) {
        LocalDate dateCourse = LocalDate.parse(resultList.get(0).getValues().get(0).getDatetime());
        double rubCourse = Double.parseDouble(resultList.get(0).getValues().get(0).getClose());
        double kztCourse = Double.parseDouble(resultList.get(1).getValues().get(0).getClose());
        rep.save(new CourseMongoDto(1, dateCourse, rubCourse, kztCourse));
    }*/

    public List<CourseTranslationEntity> getAllCourses() {
        return repository.findAll();
    }

}
