package com.example.college.api;

import com.example.college.dto.StudentDto;
import com.example.college.model.UserModel;
import com.example.college.service.MyUserService;
import com.example.college.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserModelApi {

    private final MyUserService myUserService;
    private final StudentService studentService;

    @PostMapping("/registr")
    public void registr(@RequestBody UserModel model) {
        myUserService.registr(model);
    }

    @GetMapping("/students")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }
}
