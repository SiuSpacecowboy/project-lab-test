package com.example.labtestproject.entity;

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
public class CourseTranslationEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "course_date")
        private LocalDate courseDate;

        @Column(name = "course_usd_rub")
        private double courseUsdRub;

        @Column(name = "course_usd_kzt")
        private double courseUsdKzt;

        public CourseTranslationEntity(LocalDate courseDate, double courseUsdRub, double courseUsdKzt) {
            this.courseDate = courseDate;
            this.courseUsdRub = courseUsdRub;
            this.courseUsdKzt = courseUsdKzt;
        }
}
