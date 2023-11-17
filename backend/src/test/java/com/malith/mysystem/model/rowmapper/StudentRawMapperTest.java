package com.malith.mysystem.model.rowmapper;

import com.malith.mysystem.entity.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentRawMapperTest {
    @Test
    void mapRow() throws SQLException {
        // Given
        StudentRawMapper studentRawMapper = new StudentRawMapper();

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Malith");
        when(resultSet.getString("address")).thenReturn("Galle");
        when(resultSet.getInt("age")).thenReturn(22);
        when(resultSet.getString("email")).thenReturn("m@gmail.com");
        when(resultSet.getString("gender")).thenReturn("male");

        // When
        Student actual = studentRawMapper.mapRow(resultSet, 1);

        // Then
        Student expected = new Student(
          1,"Malith","Galle",22,"m@gmail.com","male"
        );

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}