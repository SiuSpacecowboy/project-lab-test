package com.example.labtestproject.repositories;

import com.example.labtestproject.entity.CourseMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseMongoRepository extends MongoRepository<CourseMongoEntity, Long> {
}
