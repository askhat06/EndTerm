package com.example.college.mapper;
import com.example.college.dto.StudentDto;
import com.example.college.model.Course;
import com.example.college.model.Student;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }

        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());

        if (student.getCourses() != null) {
            Set<Long> courseIds = student.getCourses().stream()
                    .map(Course::getId)
                    .collect(Collectors.toSet());
            dto.setCourseIds(courseIds);
        }

        return dto;
    }

    public Student fromDtoForCreate(StudentDto dto) {
        if (dto == null) {
            return null;
        }

        return Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    public void updateEntityFromDto(StudentDto dto, Student entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
    }
}
