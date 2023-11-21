package com.malith.mysystem.service.serviceImpl;

import com.malith.mysystem.dao.StudentDao;
import com.malith.mysystem.dto.request.StudentRequestDto;
import com.malith.mysystem.dto.response.StudentResponseDto;
import com.malith.mysystem.entity.Student;
import com.malith.mysystem.exception.DuplicateResourceException;
import com.malith.mysystem.exception.RequestValidationException;
import com.malith.mysystem.exception.ResourceNotFoundException;
import com.malith.mysystem.service.StudentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;
    private final StudentDao studentDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(ModelMapper modelMapper,
                              @Qualifier("jdbc") StudentDao studentDao, PasswordEncoder passwordEncoder) {
        this.modelMapper= modelMapper;
        this.studentDao = studentDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public StudentResponseDto getStudentById(Long id) {
        Optional<Student> student = studentDao.findStudentByID(id);
        if (student.isEmpty()){
            throw new ResourceNotFoundException("Student with id %s not found".formatted(id));
        }
        return modelMapper.map(student, StudentResponseDto.class);
    }

    @Override
    public void saveStudent(StudentRequestDto studentRequestDto) {

        if (studentDao.existsStudentByEmail(studentRequestDto.getEmail())){
            throw new DuplicateResourceException("Student email %s Already Exist".formatted(studentRequestDto.getEmail()));
        }else {
            Student student = modelMapper.map(studentRequestDto, Student.class);
            student.setPassword(passwordEncoder.encode(studentRequestDto.getPassword()));
            studentDao.save(student);
            //return modelMapper.map(savedStudent,StudentResponseDto.class);
        }
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {
        List<Student> students = studentDao.findAllStudents();
        return modelMapper.map(students,new TypeToken<List<StudentResponseDto>>(){}.getType());
    }

    @Override
    public boolean existStudentByEmail(String email) {
        return studentDao.existsStudentByEmail(email);
    }

    @Override
    public String deleteStudentById(Long id) {
        StudentResponseDto studentById = getStudentById(id);
        if (studentById==null){
            throw new ResourceNotFoundException("User id %s not found".formatted(id));
        }else {
            studentDao.studentDeleteById(id);
            return "Success";
        }
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto) {
        StudentResponseDto student = getStudentById(id);

        boolean changes = false;

        if (studentRequestDto.getName()!=null && !student.getName().equals(studentRequestDto.getName())){
            student.setName(studentRequestDto.getName());
            changes = true;
        }

        if (studentRequestDto.getAddress()!=null && !studentRequestDto.getAddress().equals(student.getAddress())){
            student.setAddress(studentRequestDto.getAddress());
            changes = true;
        }

        if (studentRequestDto.getAge()!=0 && studentRequestDto.getAge()!=student.getAge()){
            student.setAge(studentRequestDto.getAge());
            changes = true;
        }
        if (studentRequestDto.getEmail()!=null && !studentRequestDto.getEmail().isEmpty() && !studentRequestDto.getEmail().equals(student.getEmail())){
            if (existStudentByEmail(studentRequestDto.getEmail())){
                throw new DuplicateResourceException(
                        "Student email %s Already Exist".formatted(studentRequestDto.getEmail())
                );
            }
            student.setEmail(studentRequestDto.getEmail());
            changes = true;
        }
        if (studentRequestDto.getGender()!=null && !studentRequestDto.getGender().equals(student.getGender())){
            student.setGender(studentRequestDto.getGender());
            changes = true;
        }

        if (!changes){
            throw new RequestValidationException("No data change found");
        }
        studentDao.updateStudent(student);
        return student;

    }
}
