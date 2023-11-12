package com.malith.mysystem.dao.jdbc;

import com.malith.mysystem.dao.StudentDao;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;
import com.malith.mysystem.model.rowmapper.StudentRawMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("jdbc")
public class StudentJDBCDataAccessService implements StudentDao {
    private final JdbcTemplate jdbcTemplate;
    private final StudentRawMapper studentRawMapper;

    @Autowired
    public StudentJDBCDataAccessService(JdbcTemplate jdbcTemplate,
                                        StudentRawMapper studentRawMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRawMapper = studentRawMapper;
    }

    @Override
    public Optional<Student> findStudentByID(Long id) {
        var sql = """
                SELECT * FROM student where id = ?
                """;
        return jdbcTemplate.query(sql, studentRawMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsStudentByEmail(String email) {
        var sql = """
                SELECT count(id) FROM student where email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count !=null && count>0;
    }

    @Override
    public void save(Student student) {
        var sql = """
                INSERT INTO student (name,address,age,email)
                VALUES (?, ?, ?, ?)
                """;
        int update = jdbcTemplate.update(sql, student.getName(), student.getAddress(), student.getAge(), student.getEmail());
        System.out.println("insert result "+update);

    }

    @Override
    public List<Student> findAllStudents() {
        var sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, studentRawMapper);
    }

    @Override
    public void studentDeleteById(Long id) {
        var sql = "DELETE FROM student WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void updateStudent(StudentResponseDto student) {
        var sql = "UPDATE student SET name =?, address =?, age =?, email =? where id = ?";

        jdbcTemplate.update(sql,student.getName(),student.getAddress(),student.getAge(),student.getEmail(), student.getStudentId());
    }
}
