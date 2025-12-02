package com.example.college.dto;
import lombok.Data;
import java.util.Set;

@Data
public class CourseDto {

    private Long id;
    private String code;
    private String name;
    private String description;

    private Set<Long> studentIds;
}
