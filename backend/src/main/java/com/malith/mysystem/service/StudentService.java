package com.malith.mysystem.service;

import com.malith.mysystem.dto.request.StudentRequestDto;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public StudentResponseDto getStudentById(Long id);

    void saveStudent(StudentRequestDto studentRequestDto);

    List<StudentResponseDto> getAllStudents();

    boolean existStudentByEmail(String name);

    String deleteStudentById(Long id);

    StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto);
}
