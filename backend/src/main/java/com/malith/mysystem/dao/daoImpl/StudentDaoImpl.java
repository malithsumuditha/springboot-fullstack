package com.malith.mysystem.dao.daoImpl;

import com.malith.mysystem.dao.StudentDao;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;
import com.malith.mysystem.repo.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDaoImpl implements StudentDao {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentDaoImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Student> findStudentByID(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public boolean existsStudentByEmail(String email) {
        return studentRepository.existsStudentByEmail(email);
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void studentDeleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void updateStudent(StudentResponseDto student) {
        studentRepository.save(modelMapper.map(student, Student.class));
    }
}
