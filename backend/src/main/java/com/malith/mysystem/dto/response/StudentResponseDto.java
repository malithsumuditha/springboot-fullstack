package com.malith.mysystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudentResponseDto {

    private long studentId;
    private String name;
    private String address;
    private int age;
    private String email;
    private String gender;

    public StudentResponseDto( String name, String address, int age, String email, String gender) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.email = email;
        this.gender = gender;
    }
}
