package org.ecnu.backend.service;

import org.ecnu.backend.entity.Surveyor;
import org.ecnu.backend.mapper.SurveyorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyorService {
    @Autowired
    private SurveyorMapper surveyorMapper;

    public Surveyor createSurveyor(Surveyor surveyor) {
        surveyorMapper.insertSurveyor(surveyor);
        return surveyor;
    }

    public Surveyor getSurveyorById(Long id) {
        return surveyorMapper.getSurveyorById(id);
    }

    public List<Surveyor> getSurveyorByName(String name) {
        return surveyorMapper.getSurveyorByName(name);
    }

    public List<Surveyor> getAllSurveyors() {
        return surveyorMapper.getAllSurveyors();
    }
}