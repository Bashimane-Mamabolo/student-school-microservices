package com.bash.student.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {
    @Id
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Integer schoolId;


}
