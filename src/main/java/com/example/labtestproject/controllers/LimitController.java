package com.example.labtestproject.controllers;

import com.example.labtestproject.entity.LimitEntity;
import com.example.labtestproject.services.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, обрабатывающий взаимодействия с лимитами определенного аккаунта. */
@RestController
@RequestMapping(value = "api/v1/acc/{id}/limits")
public class LimitController {

    private final LimitService limitService;

    @Autowired
    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @GetMapping
    public ResponseEntity<?> findAllLimitsById(@PathVariable("id") long id) {
        return limitService.findLimByAccId(id);
    }

    @PostMapping("/create-limit")
    public ResponseEntity<String> addNewOneLimit(@PathVariable("id") long id, @RequestBody LimitEntity limitEntity) {
        return limitService.saveLimitInAcc(limitEntity, id);
    }

    @GetMapping("/all-true-limits")
    public ResponseEntity<List<Object[]>> findAllFlags(@PathVariable("id") long id) {
        return limitService.findTransWithFlagsByAccId(id);
    }
}
