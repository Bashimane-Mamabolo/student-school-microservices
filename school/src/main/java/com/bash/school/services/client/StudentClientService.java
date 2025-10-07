package com.bash.school.services.client;

import com.bash.school.models.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "student-service",
    url = "${application.config.students-url}"
)
@Service
public interface StudentClientService {
    @GetMapping("/school/{school-id}")
    List<Student> findAllStudentsBySchool(
            @PathVariable("school-id") Integer schoolId
    );

}
