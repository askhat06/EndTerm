package com.example.college.mapper;
import com.example.college.dto.CourseDto;
import com.example.college.model.Course;
import com.example.college.model.Student;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public CourseDto toDto(Course course) {
        if (course == null) {
            return null;
        }

        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());

        if (course.getStudents() != null) {
            Set<Long> studentIds = course.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toSet());
            dto.setStudentIds(studentIds);
        }

        return dto;
    }

    public Course fromDtoForCreate(CourseDto dto) {
        if (dto == null) {
            return null;
        }

        return Course.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public void updateEntityFromDto(CourseDto dto, Course entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }
}
