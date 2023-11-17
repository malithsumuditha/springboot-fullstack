package com.malith.mysystem.service.serviceImpl;

import com.malith.mysystem.dao.StudentDao;
import com.malith.mysystem.dto.request.StudentRequestDto;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;
import com.malith.mysystem.exception.DuplicateResourceException;
import com.malith.mysystem.exception.RequestValidationException;
import com.malith.mysystem.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentDao studentDao;
    private StudentServiceImpl underTest;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        underTest = new StudentServiceImpl(modelMapper, studentDao);
    }

    @Test
    void getStudentById() {
        // Given
        long id = 1L;
        Student student = new Student(
                id, "malith", "Matara", 19, "m@gmail.com","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        // When
        StudentResponseDto studentById = underTest.getStudentById(id);
        // Then
        assertThat(studentById.getName()).isEqualTo(student.getName());
    }

    @Test
    void saveStudent() {
        // Given
        String email = "m@gmail.com";
        when(studentDao.existsStudentByEmail(email)).thenReturn(false);

        StudentRequestDto requestDto = new StudentRequestDto(
                "Malith", "Matara", 20, email,"male"
        );


        // When
        underTest.saveStudent(requestDto);

        // Then
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentDao).save(studentCaptor.capture());
        Student studentCaptorValue = studentCaptor.getValue();

        assertThat(studentCaptorValue.getName()).isEqualTo(requestDto.getName());
        assertThat(studentCaptorValue.getAddress()).isEqualTo(requestDto.getAddress());
        assertThat(studentCaptorValue.getAge()).isEqualTo(requestDto.getAge());
        assertThat(studentCaptorValue.getEmail()).isEqualTo(requestDto.getEmail());
        assertThat(studentCaptorValue.getGender()).isEqualTo(requestDto.getGender());

    }

    @Test
    void willThrowWhenEmailExistsWhileSaveStudent() {
        // Given
        String email = "m@gmail.com";
        when(studentDao.existsStudentByEmail(email)).thenReturn(true);

        StudentRequestDto requestDto = new StudentRequestDto(
                "Malith", "Matara", 20, email,"male"
        );


        // When
        assertThatThrownBy(() -> underTest.saveStudent(requestDto)).isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Student email %s Already Exist".formatted(requestDto.getEmail()));

        // Then

        verify(studentDao, never()).save(any());


    }

    @Test
    void getAllStudents() {
        // Given

        // When
        underTest.getAllStudents();

        // Then
        verify(studentDao).findAllStudents();
    }

    @Test
    void existStudentByEmail() {
        // Given
        String email = "m@gmail.com";
        when(studentDao.existsStudentByEmail(email)).thenReturn(true);

        // When
        underTest.existStudentByEmail(email);

        // Then
        verify(studentDao).existsStudentByEmail(email);
    }

    @Test
    void deleteStudentById() {
        // Given
        long id = 1;
        Student student = new Student(id, "m", "a", 19, "a","male");
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        // When
        underTest.deleteStudentById(id);

        // Then
        verify(studentDao).studentDeleteById(id);
    }

    @Test
    void WillThrowNoStudentIdWhileDeleteStudentById() {
        // Given
        long id = 1;
        //Student student = new Student(id, "m", "a", 19, "a");
        when(studentDao.findStudentByID(id)).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> underTest.deleteStudentById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student with id %s not found".formatted(id));


        // Then
        verify(studentDao, never()).studentDeleteById(any());
    }

    @Test
    void updateStudent() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                "Malith","galle",21, newEmail,"male"
        );

        when(studentDao.existsStudentByEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateStudent(id,studentRequestDto);

        // Then
        ArgumentCaptor<StudentResponseDto> studentResponseDtoArgumentCaptor =
                ArgumentCaptor.forClass(StudentResponseDto.class);

        verify(studentDao).updateStudent(studentResponseDtoArgumentCaptor.capture());

        StudentResponseDto captorStudent = studentResponseDtoArgumentCaptor.getValue();

        assertThat(captorStudent.getName()).isEqualTo(studentRequestDto.getName());
        assertThat(captorStudent.getAddress()).isEqualTo(studentRequestDto.getAddress());
        assertThat(captorStudent.getAge()).isEqualTo(studentRequestDto.getAge());
        assertThat(captorStudent.getEmail()).isEqualTo(studentRequestDto.getEmail());
        assertThat(captorStudent.getGender()).isEqualTo(studentRequestDto.getGender());
    }

    @Test
    void updateStudentNameOnly() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                "Malith",null,0, null,null
        );

        // When
        underTest.updateStudent(id,studentRequestDto);

        // Then
        ArgumentCaptor<StudentResponseDto> studentResponseDtoArgumentCaptor =
                ArgumentCaptor.forClass(StudentResponseDto.class);

        verify(studentDao).updateStudent(studentResponseDtoArgumentCaptor.capture());

        StudentResponseDto captorStudent = studentResponseDtoArgumentCaptor.getValue();

        assertThat(captorStudent.getName()).isEqualTo(studentRequestDto.getName());
        assertThat(captorStudent.getAddress()).isEqualTo(student.getAddress());
        assertThat(captorStudent.getAge()).isEqualTo(student.getAge());
        assertThat(captorStudent.getEmail()).isEqualTo(student.getEmail());
    }

    @Test
    void updateStudentOnlyEmail() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                null,null,0, newEmail,null
        );

        when(studentDao.existsStudentByEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateStudent(id,studentRequestDto);

        // Then
        ArgumentCaptor<StudentResponseDto> studentResponseDtoArgumentCaptor =
                ArgumentCaptor.forClass(StudentResponseDto.class);

        verify(studentDao).updateStudent(studentResponseDtoArgumentCaptor.capture());

        StudentResponseDto captorStudent = studentResponseDtoArgumentCaptor.getValue();

        assertThat(captorStudent.getName()).isEqualTo(student.getName());
        assertThat(captorStudent.getAddress()).isEqualTo(student.getAddress());
        assertThat(captorStudent.getAge()).isEqualTo(student.getAge());
        assertThat(captorStudent.getEmail()).isEqualTo(newEmail);
        assertThat(captorStudent.getGender()).isEqualTo(student.getGender());
    }

    @Test
    void ThrowExceptionUpdateStudentOnlyEmail() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                null,null,0, newEmail,null
        );

        when(studentDao.existsStudentByEmail(newEmail)).thenReturn(true);

        // When

        assertThatThrownBy(() -> underTest.updateStudent(id,studentRequestDto))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Student email %s Already Exist".formatted(newEmail));

        // Then

        verify(studentDao,never()).updateStudent(any());

    }

    @Test
    void willThrowExceptionNoDataForUpdate() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                student.getName(),student.getAddress(),student.getAge(), student.getEmail(), student.getGender()
        );

        // When

        assertThatThrownBy(() -> underTest.updateStudent(id,studentRequestDto))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No data change found");

        // Then

        verify(studentDao,never()).updateStudent(any());

    }

    @Test
    void updateStudentAddressOnly() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                null,"Galle",0, null,null
        );

        // When
        underTest.updateStudent(id,studentRequestDto);

        // Then
        ArgumentCaptor<StudentResponseDto> studentResponseDtoArgumentCaptor =
                ArgumentCaptor.forClass(StudentResponseDto.class);

        verify(studentDao).updateStudent(studentResponseDtoArgumentCaptor.capture());

        StudentResponseDto captorStudent = studentResponseDtoArgumentCaptor.getValue();

        assertThat(captorStudent.getName()).isEqualTo(student.getName());
        assertThat(captorStudent.getAddress()).isEqualTo(studentRequestDto.getAddress());
        assertThat(captorStudent.getAge()).isEqualTo(student.getAge());
        assertThat(captorStudent.getEmail()).isEqualTo(student.getEmail());
        assertThat(captorStudent.getGender()).isEqualTo(student.getGender());
    }

    @Test
    void updateStudentAgeOnly() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                null,null,22, null,null
        );

        // When
        underTest.updateStudent(id,studentRequestDto);

        // Then
        ArgumentCaptor<StudentResponseDto> studentResponseDtoArgumentCaptor =
                ArgumentCaptor.forClass(StudentResponseDto.class);

        verify(studentDao).updateStudent(studentResponseDtoArgumentCaptor.capture());

        StudentResponseDto captorStudent = studentResponseDtoArgumentCaptor.getValue();

        assertThat(captorStudent.getName()).isEqualTo(student.getName());
        assertThat(captorStudent.getAddress()).isEqualTo(student.getAddress());
        assertThat(captorStudent.getAge()).isEqualTo(studentRequestDto.getAge());
        assertThat(captorStudent.getEmail()).isEqualTo(student.getEmail());
        assertThat(captorStudent.getGender()).isEqualTo(student.getGender());
    }

    @Test
    void updateStudentGenderOnly() {
        // Given
        long id =1;
        Student student = new Student(
                id,"m","a",19,"ma","male"
        );
        when(studentDao.findStudentByID(id)).thenReturn(Optional.of(student));

        String newEmail = "m@gmail.com";
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                null,null,0, null,"female"
        );

        // When
        underTest.updateStudent(id,studentRequestDto);

        // Then
        ArgumentCaptor<StudentResponseDto> studentResponseDtoArgumentCaptor =
                ArgumentCaptor.forClass(StudentResponseDto.class);

        verify(studentDao).updateStudent(studentResponseDtoArgumentCaptor.capture());

        StudentResponseDto captorStudent = studentResponseDtoArgumentCaptor.getValue();

        assertThat(captorStudent.getName()).isEqualTo(student.getName());
        assertThat(captorStudent.getAddress()).isEqualTo(student.getAddress());
        assertThat(captorStudent.getAge()).isEqualTo(student.getAge());
        assertThat(captorStudent.getEmail()).isEqualTo(student.getEmail());
        assertThat(captorStudent.getGender()).isEqualTo(studentRequestDto.getGender());
    }
}