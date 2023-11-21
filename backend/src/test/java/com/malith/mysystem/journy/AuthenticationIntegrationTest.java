package com.malith.mysystem.journy;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.malith.mysystem.auth.AuthenticationRequest;
import com.malith.mysystem.auth.AuthenticationResponse;
import com.malith.mysystem.dto.request.StudentRequestDto;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.jwt.JWTUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationIntegrationTest {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private WebTestClient webTestClient;
    private static final Random RANDOM = new Random();
    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String STUDENT_URI = "/api/v1/student/";

    @Test
    void canLogin() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String address = faker.address().fullAddress();
        int age = RANDOM.nextInt(10, 50);
        String email = fakerName.firstName() + UUID.randomUUID() + "@testing.com";
        String gender = "male";

        StudentRequestDto studentRequestDto = new StudentRequestDto(
                name, address, age, email, gender,"password"
        );
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                email,
                "password"
        );

        webTestClient.post()
                .uri(AUTHENTICATION_PATH+"/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();

        //send post request
        webTestClient.post()
                .uri(STUDENT_URI + "save-student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(studentRequestDto), StudentRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk();

        //try to Login
        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                })
                .returnResult();

        String jwtToken = result.getResponseHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        AuthenticationResponse responseBody = result.getResponseBody();

        //StudentResponseDto studentResponseDto = responseBody.studentResponse();


        Assertions.assertThat(jwtUtil.isTokenValid(jwtToken,authenticationRequest.username())).isTrue();

//        Assertions.assertThat(studentResponseDto.getEmail()).isEqualTo(email);
//        Assertions.assertThat(studentResponseDto.getUsername()).isEqualTo(email);
//        Assertions.assertThat(studentResponseDto.getAge()).isEqualTo(age);
//        Assertions.assertThat(studentResponseDto.getGender()).isEqualTo(gender);
//        Assertions.assertThat(studentResponseDto.getAddress()).isEqualTo(address);

    }
}
