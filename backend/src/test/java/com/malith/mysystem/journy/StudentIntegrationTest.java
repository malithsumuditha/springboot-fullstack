package com.malith.mysystem.journy;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.malith.mysystem.dto.request.StudentRequestDto;
import com.malith.mysystem.dto.response.StudentResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    private static final Random RANDOM = new Random();
    private static final String STUDENT_URI = "/api/v1/student/";

    @Test
    void canRegisterStudent() {

        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String address = faker.address().fullAddress();
        int age = RANDOM.nextInt(10, 50);
        String email = fakerName.firstName() + UUID.randomUUID() + "@testing.com";
        String gender = "male";

        StudentRequestDto studentRequestDto = new StudentRequestDto(
                name, address, age, email, gender
        );

        //send post request
        webTestClient.post()
                .uri(STUDENT_URI + "save-student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(studentRequestDto), StudentRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all students
        List<StudentResponseDto> allStudents = webTestClient.get()
                .uri(STUDENT_URI + "get-students")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<StudentResponseDto>() {
                })
                .returnResult()
                .getResponseBody();

        //make sure that student is present
        StudentResponseDto expectedStudent = new StudentResponseDto(
                name, address, age, email,gender
        );

        Assertions.assertThat(allStudents)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("studentId")
                .contains(expectedStudent);

        var id = allStudents.stream()
                .filter(student -> student.getEmail().equals(email))
                .map(StudentResponseDto::getStudentId)
                .findFirst()
                .orElseThrow();

        expectedStudent.setStudentId(id);
        //get student by id

        webTestClient.get()
                .uri(STUDENT_URI + "get-student/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<StudentResponseDto>() {
                })
                .isEqualTo(expectedStudent);
    }

    @Test
    void canDeleteCustomer() {
// create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String address = faker.address().fullAddress();
        int age = RANDOM.nextInt(10, 50);
        String email = fakerName.firstName() + UUID.randomUUID() + "@testing.com";
        String gender = "male";

        StudentRequestDto studentRequestDto = new StudentRequestDto(
                name, address, age, email,gender
        );

        //send post request
        webTestClient.post()
                .uri(STUDENT_URI + "save-student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(studentRequestDto), StudentRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all students
        List<StudentResponseDto> allStudents = webTestClient.get()
                .uri(STUDENT_URI + "get-students")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<StudentResponseDto>() {
                })
                .returnResult()
                .getResponseBody();


        var id = allStudents.stream()
                .filter(student -> student.getEmail().equals(email))
                .map(StudentResponseDto::getStudentId)
                .findFirst()
                .orElseThrow();

        //delete student
        webTestClient.delete()
                .uri(STUDENT_URI + "delete-student/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
        //get student by id

        webTestClient.get()
                .uri(STUDENT_URI + "get-student/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
     // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String address = faker.address().fullAddress();
        int age = RANDOM.nextInt(10, 50);
        String email = fakerName.firstName() + UUID.randomUUID() + "@testing.com";
        String gender = "male";

        StudentRequestDto studentRequestDto = new StudentRequestDto(
                name, address, age, email,gender
        );

        //send post request
        webTestClient.post()
                .uri(STUDENT_URI + "save-student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(studentRequestDto), StudentRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all students
        List<StudentResponseDto> allStudents = webTestClient.get()
                .uri(STUDENT_URI + "get-students")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<StudentResponseDto>() {
                })
                .returnResult()
                .getResponseBody();


        var id = allStudents.stream()
                .filter(student -> student.getEmail().equals(email))
                .map(StudentResponseDto::getStudentId)
                .findFirst()
                .orElseThrow();

        String newName = "HPMS Udara";
        StudentRequestDto updateRequestDto = new StudentRequestDto(
                  newName,null,0,null,null
        );

        //update student
        webTestClient.put()
                .uri(STUDENT_URI + "update-student/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequestDto), StudentRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get student by id

        StudentResponseDto updatedStudent = webTestClient.get()
                .uri(STUDENT_URI + "get-student/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(StudentResponseDto.class)
                .returnResult()
                .getResponseBody();

        StudentResponseDto expectStudent = new StudentResponseDto(
                id,newName,address,age,email,gender
        );

        Assertions.assertThat(updatedStudent).isEqualTo(expectStudent);
    }
}
