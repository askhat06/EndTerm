package com.example.college.mapper;

import com.example.college.dto.StudentDto;
import com.example.college.model.Course;
import com.example.college.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void convertEntityToDtoTest() {

        Course course1 = Course.builder().id(1L).code("CS101").name("Intro").build();
        Course course2 = Course.builder().id(2L).code("MATH101").name("Math").build();

        Set<Course> courses = new HashSet<>();
        courses.add(course1);
        courses.add(course2);

        Student entity = Student.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .courses(courses)
                .build();

        StudentDto dto = studentMapper.toDto(entity);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getFirstName());
        Assertions.assertNotNull(dto.getLastName());
        Assertions.assertNotNull(dto.getEmail());
        Assertions.assertNotNull(dto.getCourseIds());

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getFirstName(), dto.getFirstName());
        Assertions.assertEquals(entity.getLastName(), dto.getLastName());
        Assertions.assertEquals(entity.getEmail(), dto.getEmail());
        Assertions.assertEquals(courses.size(), dto.getCourseIds().size());
    }

    @Test
    void convertDtoToEntityTest() {

        StudentDto dto = new StudentDto();
        dto.setId(5L);
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        dto.setEmail("jane@example.com");

        Student entity = studentMapper.fromDtoForCreate(dto);

        Assertions.assertNotNull(entity);

        Assertions.assertNotNull(entity.getFirstName());
        Assertions.assertNotNull(entity.getLastName());
        Assertions.assertNotNull(entity.getEmail());

        Assertions.assertEquals(dto.getFirstName(), entity.getFirstName());
        Assertions.assertEquals(dto.getLastName(), entity.getLastName());
        Assertions.assertEquals(dto.getEmail(), entity.getEmail());
    }

    @Test
    void convertListEntityStudentToListDtoStudentTest() {

        List<Student> entityList = new ArrayList<>();

        entityList.add(Student.builder().id(1L).firstName("A").lastName("B").email("a@example.com").build());
        entityList.add(Student.builder().id(2L).firstName("C").lastName("D").email("c@example.com").build());
        entityList.add(Student.builder().id(3L).firstName("E").lastName("F").email("e@example.com").build());

        List<StudentDto> dtoList = new ArrayList<>();

        for (Student student : entityList) {
            dtoList.add(studentMapper.toDto(student));
        }

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(entityList.size(), dtoList.size());

        for (int i = 0; i < entityList.size(); i++) {
            Student entity = entityList.get(i);
            StudentDto dto = dtoList.get(i);

            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getFirstName());
            Assertions.assertNotNull(dto.getLastName());
            Assertions.assertNotNull(dto.getEmail());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getFirstName(), dto.getFirstName());
            Assertions.assertEquals(entity.getLastName(), dto.getLastName());
            Assertions.assertEquals(entity.getEmail(), dto.getEmail());
        }
    }
}