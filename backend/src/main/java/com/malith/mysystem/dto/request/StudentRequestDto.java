package com.malith.mysystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudentRequestDto {
    //private long studentId;
    private String name;
    private String address;
    private int age;
    private String email;
}
