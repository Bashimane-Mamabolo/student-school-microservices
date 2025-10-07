package com.bash.school.services;

import com.bash.school.models.CompleteSchoolResponse;
import com.bash.school.models.School;
import com.bash.school.repository.SchoolRepository;
import com.bash.school.services.client.StudentClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final StudentClientService studentClientService;

    public void saveSchool(School school){
        schoolRepository.save(school);
    }

    public List<School> findAllSchools(){
        return schoolRepository.findAll();
    }

    public CompleteSchoolResponse findSchoolsWithStudents(Integer schoolId) {

        var school = schoolRepository.findById(schoolId)
                .orElse(
                        School.builder()
                                .name("NotFound")
                                .email("NotFound")
                                .build()
                );
        // Find all students from this school (schoolId) from the student microservices
        var students = studentClientService.findAllStudentsBySchool(schoolId);
        return CompleteSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
