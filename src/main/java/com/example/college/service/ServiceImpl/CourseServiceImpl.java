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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<Course> entityList = courseRepository.findAll();
        List<CourseDto> dtoList = new ArrayList<>();

        for (Course course : entityList) {
            CourseDto dto = courseMapper.toDto(course);
            if (dto != null) {
                dtoList.add(dto);
            }
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.fromDtoForCreate(courseDto);

        if (courseDto != null && courseDto.getStudentIds() != null && !courseDto.getStudentIds().isEmpty()) {
            List<Student> students = studentRepository.findAllById(courseDto.getStudentIds());
            Set<Student> studentSet = new HashSet<>(students);
            course.setStudents(studentSet);
        }

        Course saved = courseRepository.save(course);

        return courseMapper.toDto(saved);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Course existing = courseRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        courseMapper.updateEntityFromDto(courseDto, existing);

        if (courseDto != null && courseDto.getStudentIds() != null) {
            List<Student> students = studentRepository.findAllById(courseDto.getStudentIds());
            Set<Student> studentSet = new HashSet<>(students);
            existing.setStudents(studentSet);
        }

        Course updated = courseRepository.save(existing);

        return courseMapper.toDto(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        Course existing = courseRepository.findById(id).orElse(null);

        if (existing != null) {
            courseRepository.delete(existing);
        }
    }
}