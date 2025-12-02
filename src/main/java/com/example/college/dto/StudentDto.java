package com.example.college.dto;
import lombok.Data;
import java.util.Set;

@Data
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<Long> courseIds;
}
