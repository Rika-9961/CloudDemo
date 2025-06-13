package com.hitwh.service;

import com.hitwh.entity.Student;
import com.hitwh.repository.StudentRepository;
import com.hitwh.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Student register(StudentRegistrationDTO dto) {

        if (studentRepository.existsByStudentId(dto.getStudentId())) {
            throw new StudentIdAlreadyExistsException("Student ID already exists");
        }

        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setMajor(dto.getMajor());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));

        return studentRepository.save(student);
    }

    public String login(LoginRequest request) {
        Student student = studentRepository.findByStudentId(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }

        return jwtUtil.generateToken(student.getStudentId(), student.getRole().name());
    }


}
