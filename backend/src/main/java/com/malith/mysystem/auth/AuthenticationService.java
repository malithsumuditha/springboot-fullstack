package com.malith.mysystem.auth;

import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;
import com.malith.mysystem.jwt.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, ModelMapper modelMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        Student principal = (Student) authentication.getPrincipal();
        StudentResponseDto student = modelMapper.map(principal, StudentResponseDto.class);
        String accessToken = jwtUtil.issueToken(student.getUsername(), "ROLE_USER");
        //jwtUtil.issueToken(student.getUsername(),student.getRoles);

        return new AuthenticationResponse(
                accessToken
        );

    }
}
