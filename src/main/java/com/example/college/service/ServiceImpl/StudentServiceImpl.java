package com.example.college.service.ServiceImpl;
import com.example.college.dto.StudentDto;
import com.example.college.mapper.StudentMapper;
import com.example.college.model.Course;
import com.example.college.model.Student;
import com.example.college.repository.CourseRepository;
import com.example.college.repository.StudentRepository;
import com.example.college.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository,
                              CourseRepository courseRepository,
                              StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        List<Student> entityList = studentRepository.findAll();
        List<StudentDto> dtoList = new ArrayList<>();

        for (Student student : entityList) {
            StudentDto dto = studentMapper.toDto(student);
            if (dto != null) {
                dtoList.add(dto);
            }
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = studentMapper.fromDtoForCreate(studentDto);

        if (studentDto != null && studentDto.getCourseIds() != null && !studentDto.getCourseIds().isEmpty()) {
            List<Course> courses = courseRepository.findAllById(studentDto.getCourseIds());
            Set<Course> courseSet = new HashSet<>(courses);
            student.setCourses(courseSet);
        }

        Student saved = studentRepository.save(student);

        return studentMapper.toDto(saved);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student existing = studentRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        studentMapper.updateEntityFromDto(studentDto, existing);

        if (studentDto != null && studentDto.getCourseIds() != null) {
            List<Course> courses = courseRepository.findAllById(studentDto.getCourseIds());
            Set<Course> courseSet = new HashSet<>(courses);
            existing.setCourses(courseSet);
        }

        Student updated = studentRepository.save(existing);

        return studentMapper.toDto(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        Student existing = studentRepository.findById(id).orElse(null);

        if (existing != null) {
            studentRepository.delete(existing);
        }
    }
}
