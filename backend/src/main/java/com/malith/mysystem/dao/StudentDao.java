package com.malith.mysystem.dao;

import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Optional<Student> findStudentByID(Long id);

    boolean existsStudentByEmail(String email);

    void save(Student student);

    List<Student> findAllStudents();

    void studentDeleteById(Long id);

    void updateStudent(StudentResponseDto student);
}
