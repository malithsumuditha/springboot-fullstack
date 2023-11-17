package com.malith.mysystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_customer_email",
                        columnNames = "email"
                )
        }
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long studentId;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "address")
    private String address;
    private int age;
    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;
}
