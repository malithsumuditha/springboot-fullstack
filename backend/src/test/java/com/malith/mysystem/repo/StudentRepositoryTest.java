package com.malith.mysystem.repo;

import com.malith.mysystem.AbstractTestContainerUnitTest;
import com.malith.mysystem.entity.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest extends AbstractTestContainerUnitTest {

    @Autowired
    private StudentRepository underTest;
    @BeforeEach
    void setUp() {
        System.out.println();
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
    void existsStudentByEmailFailsWhenEmailNotPresent() {

        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        boolean actual = underTest.existsStudentByEmail(email);

        //Then
        Assertions.assertThat(actual).isFalse();
    }
}