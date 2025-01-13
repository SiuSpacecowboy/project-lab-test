package com.example.labtestproject.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class CourseMongoEntity {

    @Id
    private long id;
    private LocalDate courseDate;
    private double courseUsdRub;
    private double courseUsdKzt;
   // private int count;

    public CourseMongoEntity(LocalDate courseDate, double courseUsdRub, double courseUsdKzt/*, int count*/) {
        this.courseDate = courseDate;
        this.courseUsdRub = courseUsdRub;
        this.courseUsdKzt = courseUsdKzt;
        //this.count = count;
    }
}
