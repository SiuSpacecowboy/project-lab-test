package com.example.labtestproject.repositories;

import com.example.labtestproject.dto.CourseTranslationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface CourseTranslationDtoRepository extends JpaRepository<CourseTranslationDto, Long> {

    @Transactional
    @Modifying
    @Query("update CourseTranslationDto c set c.courseDate = ?1, c.courseUsdRub = ?2, c.courseUsdKzt = ?3 where c.id = ?4")
    void updateCourseDateAndCourseUsdRubAndCourseUsdKztById(LocalDate courseDate, double courseUsdRub, double courseUsdKzt, long id);
}
