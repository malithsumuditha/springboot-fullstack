package com.malith.mysystem.dao.daoImpl;

import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;
import com.malith.mysystem.repo.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class StudentDaoImplTest {

    private StudentDaoImpl underTest;
    @Mock
    private StudentRepository studentRepository;
    private AutoCloseable autoCloseable;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        underTest = new StudentDaoImpl(studentRepository, modelMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findStudentByID() {
        // Given
        Long id = 1L;

        // When
        underTest.findStudentByID(id);

        // Then
        verify(studentRepository).findById(id);
    }

    @Test
    void existsStudentByEmail() {
        // Given
        String email = "m@gail.com";

        // When
        underTest.existsStudentByEmail(email);

        // Then
        verify(studentRepository).existsStudentByEmail(email);
    }

    @Test
    void save() {
        // Given
        Student student = new Student(
                1,
                "Malith",
                "Matara",
                20,
                "m@gmail.com"
        );

        // When
        underTest.save(student);

        // Then
        verify(studentRepository).save(student);
    }

    @Test
    void findAllStudents() {
        // When
        underTest.findAllStudents();

        // Then
        verify(studentRepository)
                .findAll();
    }

    @Test
    void studentDeleteById() {
        // Given
        Long id = 1L;

        // When
        underTest.studentDeleteById(id);

        // Then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void updateStudent() {
        // Given
        StudentResponseDto student = new StudentResponseDto(
                1,
                "Malith",
                "Matara",
                20,
                "m@gmail.com"
        );

        // When
        underTest.updateStudent(student);

        // Then

        verify(studentRepository).save(any(Student.class));
    }
}