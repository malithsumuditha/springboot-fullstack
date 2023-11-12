package com.malith.mysystem.dao.jdbc;

import com.github.javafaker.Faker;
import com.malith.mysystem.AbstractTestContainerUnitTest;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;
import com.malith.mysystem.model.rowmapper.StudentRawMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StudentJDBCDataAccessServiceTest extends AbstractTestContainerUnitTest {

    private StudentJDBCDataAccessService underTest;
    private final StudentRawMapper studentRawMapper = new StudentRawMapper();

    @BeforeEach
    void setUp() {
        underTest = new StudentJDBCDataAccessService(
                getJdbcTemplate(),
                studentRawMapper
        );
    }

    @Test
    void findStudentByID() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Student student = new Student(
                1,
                FAKER.name().fullName(),
                FAKER.address().fullAddress(),
                20,
                email

        );
        underTest.save(student);

        Long id = underTest.findAllStudents()
                .stream().filter(c -> c.getEmail().equals(email))
                .map(Student::getStudentId).findFirst().orElseThrow();

        // When

        Optional<Student> studentByID = underTest.findStudentByID(id);

        //Then
        Assertions.assertThat(studentByID).isPresent().hasValueSatisfying(c -> {
            Assertions.assertThat(c.getStudentId()).isEqualTo(id);
            Assertions.assertThat(c.getName()).isEqualTo(student.getName());
            Assertions.assertThat(c.getEmail()).isEqualTo(student.getEmail());
            Assertions.assertThat(c.getAge()).isEqualTo(student.getAge());
            Assertions.assertThat(c.getAddress()).isEqualTo(student.getAddress());
            System.out.println();

        });



    }

    @Test
    void existsStudentByEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Student student = new Student(
                1,
                FAKER.name().fullName(),
                FAKER.address().fullAddress(),
                20,
                email

        );
        underTest.save(student);

        //When
        boolean actual = underTest.existsStudentByEmail(email);

        //Then
        Assertions.assertThat(actual).isTrue();

    }

    @Test
    void existStudentWithEmailReturnFallsWhenDoesNotExist() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        boolean actual = underTest.existsStudentByEmail(email);

        // Then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void save() {

    }

    @Test
    void findAllStudents() {
        // Given
        Student student = new Student(
                FAKER.random().nextLong(),
                FAKER.name().fullName(),
                FAKER.address().fullAddress(),
                20,
                FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID()
        );
        underTest.save(student);

        //When
        List<Student> allStudents = underTest.findAllStudents();

        //Then

        Assertions.assertThat(allStudents).isNotEmpty();
        System.out.println();

    }

    @Test
    void studentDeleteById() {

        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Student student = new Student(
                1,
                FAKER.name().fullName(),
                FAKER.address().fullAddress(),
                20,
                email

        );
        underTest.save(student);

        Long id = underTest.findAllStudents()
                .stream().filter(c -> c.getEmail().equals(email))
                .map(Student::getStudentId).findFirst().orElseThrow();

        // When

        underTest.studentDeleteById(id);

        // Then
        Optional<Student> studentByID = underTest.findStudentByID(id);
        Assertions.assertThat(studentByID).isNotPresent();
    }

    @Test
    void updateStudent() {

        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Student student = new Student(
                1,
                FAKER.name().fullName(),
                FAKER.address().fullAddress(),
                20,
                email

        );
        underTest.save(student);

        Long id = underTest.findAllStudents()
                .stream().filter(c -> c.getEmail().equals(email))
                .map(Student::getStudentId).findFirst().orElseThrow();

        var newName = "malith";

        // When
        StudentResponseDto update = new StudentResponseDto();
        update.setStudentId(id);
        update.setName(newName);
        update.setEmail(email);

        underTest.updateStudent(update);

        // Then
        Optional<Student> studentByID = underTest.findStudentByID(id);
        Assertions.assertThat(studentByID).isPresent().hasValueSatisfying(c -> {
            Assertions.assertThat(c.getStudentId()).isEqualTo(id);
            Assertions.assertThat(c.getName()).isEqualTo(newName);
        });
    }

}