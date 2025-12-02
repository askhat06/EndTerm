package com.example.college.service.ServiceImpl;

import com.example.college.dto.CourseDto;
import com.example.college.mapper.CourseMapper;
import com.example.college.model.Course;
import com.example.college.model.Student;
import com.example.college.repository.CourseRepository;
import com.example.college.repository.StudentRepository;
import com.example.college.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository,
                             StudentRepository studentRepository,
                             CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElse(null);

        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.fromDtoForCreate(courseDto);

        if (courseDto.getStudentIds() != null && !courseDto.getStudentIds().isEmpty()) {
            List<Student> students = studentRepository.findAllById(courseDto.getStudentIds());
            course.setStudents(new HashSet<>(students));
        }

        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Course existing = courseRepository.findById(id)
                .orElse(null);

        courseMapper.updateEntityFromDto(courseDto, existing);

        if (courseDto.getStudentIds() != null) {
            List<Student> students = studentRepository.findAllById(courseDto.getStudentIds());
            Set<Student> studentSet = new HashSet<>(students);
            existing.setStudents(studentSet);
        }

        Course updated = courseRepository.save(existing);
        return courseMapper.toDto(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        Course existing = courseRepository.findById(id)
                .orElse(null);

        courseRepository.delete(existing);
    }
}
