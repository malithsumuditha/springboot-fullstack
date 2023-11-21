package com.malith.mysystem.repo;

import com.malith.mysystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsStudentByEmail(String email);
    Optional<Student> findStudentByEmail(String email);
}
