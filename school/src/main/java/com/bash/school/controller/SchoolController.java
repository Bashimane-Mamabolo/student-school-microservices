package com.bash.school.controller;

import com.bash.school.models.CompleteSchoolResponse;
import com.bash.school.models.School;
import com.bash.school.services.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @RequestBody School school
            ){
        schoolService.saveSchool(school);
    }

    @GetMapping
    public ResponseEntity<List<School>> findAllSchools(){
        return ResponseEntity.ok(schoolService.findAllSchools());
    }

    @GetMapping("with-students/{school-id}")
    public ResponseEntity<CompleteSchoolResponse> findSchoolsWithStudents(
            @PathVariable("school-id") Integer schoolId
    ){
        return ResponseEntity.ok(schoolService.findSchoolsWithStudents(schoolId));
    }
}
