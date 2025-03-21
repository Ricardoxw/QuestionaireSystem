package org.ecnu.backend.controller;

import org.ecnu.backend.entity.Surveyor;
import org.ecnu.backend.service.SurveyorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/surveyors")
public class UserController {
    @Autowired
    private SurveyorService surveyorService;

    // 创建调查者
    @PostMapping("/create")
    public Surveyor createSurveyor(@RequestBody Surveyor surveyor) {
        return surveyorService.createSurveyor(surveyor);
    }
}
