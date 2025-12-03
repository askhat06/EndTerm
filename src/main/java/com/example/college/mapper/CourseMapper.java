package com.example.college.mapper;
import com.example.college.dto.CourseDto;
import com.example.college.model.Course;
import com.example.college.model.Student;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        if (course.getStudents() != null && !course.getStudents().isEmpty()) {
            Set<Long> studentIds = new HashSet<>();

            for (Student student : course.getStudents()) {
                if (student != null && student.getId() != null) {
                    studentIds.add(student.getId());
                }
            }

            dto.setStudentIds(studentIds);
        }

        return dto;
    }

    public Course fromDtoForCreate(CourseDto dto) {
        if (dto == null) {
            return null;
        }

        Course course = new Course();

        course.setCode(dto.getCode());
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());

        return course;
    }

    public void updateEntityFromDto(CourseDto dto, Course entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }

    public List<CourseDto> toDtoList(List<Course> courseList) {
        List<CourseDto> result = new ArrayList<>();

        if (courseList == null || courseList.isEmpty()) {
            return result;
        }

        for (Course course : courseList) {
            CourseDto dto = toDto(course);
            if (dto != null) {
                result.add(dto);
            }
        }

        return result;
    }
}
