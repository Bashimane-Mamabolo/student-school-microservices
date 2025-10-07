package com.bash.school.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompleteSchoolResponse {
    private  String name;
    private String email;
    List<Student> students;

}
