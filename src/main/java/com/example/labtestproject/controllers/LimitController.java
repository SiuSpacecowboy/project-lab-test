package com.example.labtestproject.controllers;

import com.example.labtestproject.dto.LimitDto;
import com.example.labtestproject.services.LimitDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, обрабатывающий взаимодействия с лимитами определенного аккаунта. */
@RestController
@RequestMapping(value = "api/v1/acc/{id}/limits")
public class LimitController {

    private final LimitDtoService limitDtoService;

    @Autowired
    public LimitController(LimitDtoService limitDtoService) {
        this.limitDtoService = limitDtoService;
    }

    @GetMapping
    public ResponseEntity<?> findAllLimitsById(@PathVariable("id") long id) {
        return limitDtoService.findLimByAccId(id);
    }

    @PostMapping("/create-limit")
    public ResponseEntity<String> addNewOneLimit(@PathVariable("id") long id, @RequestBody LimitDto limitDto) {
        return limitDtoService.saveLimitInAcc(limitDto, id);
    }

    @GetMapping("/all-true-limits")
    public ResponseEntity<List<Object[]>> findAllFlags(@PathVariable("id") long id) {
        return limitDtoService.findTransWithFlagsByAccId(id);
    }
}
