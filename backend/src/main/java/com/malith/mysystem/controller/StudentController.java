package com.malith.mysystem.controller;

import com.malith.mysystem.dto.request.StudentRequestDto;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.exception.DuplicateResourceException;
import com.malith.mysystem.jwt.JWTUtil;
import com.malith.mysystem.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student/")
//@CrossOrigin - do it in WebMvcConfig
public class StudentController {
    private final StudentService studentService;
    private final JWTUtil jwtUtil;

    public StudentController(StudentService studentService, JWTUtil jwtUtil) {
        this.studentService = studentService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(path = "get-student/{id}")
    public StudentResponseDto getStudent(@PathVariable("id") Long id){
        return studentService.getStudentById(id);
    }

    @PostMapping(path = "save-student")
    public ResponseEntity<?> saveStudent(@RequestBody StudentRequestDto studentRequestDto){
            studentService.saveStudent(studentRequestDto);
            String jwtToken = jwtUtil.issueToken(studentRequestDto.getEmail(), "ROLE_USER");
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION,jwtToken)
                    .build();
    }

    @GetMapping(path = "get-students")
    public List<StudentResponseDto> getStudents(){
        return studentService.getAllStudents();
    }

    @DeleteMapping(path = "delete-student/{id}")
    public String deleteStudent(@PathVariable("id") Long id){
        return  studentService.deleteStudentById(id);
    }

    @PutMapping(path = "update-student/{id}")
    public StudentResponseDto updateStudent(@PathVariable("id") Long id, @RequestBody StudentRequestDto studentRequestDto){
        return studentService.updateStudent(id,studentRequestDto);
    }

}
