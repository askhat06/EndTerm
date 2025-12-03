package com.example.college.mapper;
import com.example.college.dto.StudentDto;
import com.example.college.model.Course;
import com.example.college.model.Student;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        if (student.getCourses() != null && !student.getCourses().isEmpty()) {
            Set<Long> courseIds = new HashSet<>();

            for (Course course : student.getCourses()) {
                if (course != null && course.getId() != null) {
                    courseIds.add(course.getId());
                }
            }

            dto.setCourseIds(courseIds);
        }

        return dto;
    }

    public Student fromDtoForCreate(StudentDto dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());

        return student;
    }

    public void updateEntityFromDto(StudentDto dto, Student entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
    }

    public List<StudentDto> toDtoList(List<Student> studentList) {
        List<StudentDto> result = new ArrayList<>();

        if (studentList == null || studentList.isEmpty()) {
            return result;
        }

        for (Student student : studentList) {
            StudentDto dto = toDto(student);
            if (dto != null) {
                result.add(dto);
            }
        }

        return result;
    }
}
