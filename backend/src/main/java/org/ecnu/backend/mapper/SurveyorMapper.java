package org.ecnu.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.ecnu.backend.entity.Surveyor;

import java.util.List;

@Mapper
public interface SurveyorMapper {
    @Insert("INSERT INTO surveyors (name, email, phone, organization) VALUES (#{name}, #{email}, #{phone}, #{organization})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertSurveyor(Surveyor surveyor);

    @Select("SELECT * FROM surveyors WHERE id = #{id}")
    Surveyor getSurveyorById(Long id);

    @Select("SELECT * FROM surveyors WHERE name = #{name}")
    List<Surveyor> getSurveyorByName(String name);

    @Select("SELECT * FROM surveyors")
    List<Surveyor> getAllSurveyors();

    @Insert("CREATE TABLE IF NOT EXISTS surveyors (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "email VARCHAR(255), " +
            "phone VARCHAR(20), " +
            "organization VARCHAR(255))")
    void createTable();

    @Select("SELECT COUNT(*) > 0 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'surveyors'")
    boolean checkSurveyorTableExists();
}