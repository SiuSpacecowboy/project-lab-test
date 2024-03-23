package com.example.labtestproject.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** DTO для таблицы course. */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course")
public class CourseTranslationDto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "course_date")
        private LocalDate courseDate;

        @Column(name = "course_usd_rub")
        private double courseUsdRub;

        @Column(name = "course_usd_kzt")
        private double courseUsdKzt;

        public CourseTranslationDto(LocalDate courseDate, double courseUsdRub, double courseUsdKzt) {
            this.courseDate = courseDate;
            this.courseUsdRub = courseUsdRub;
            this.courseUsdKzt = courseUsdKzt;
        }
}
