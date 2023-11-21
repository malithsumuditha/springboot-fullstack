package com.malith.mysystem.model.rowmapper;

import com.malith.mysystem.entity.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRawMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getInt("age"),
                rs.getString("email"),
                rs.getString("gender"),
                rs.getString("password")
        );
    }
}
