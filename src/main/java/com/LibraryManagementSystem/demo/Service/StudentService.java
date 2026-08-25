package com.LibraryManagementSystem.demo.Service;

import com.LibraryManagementSystem.demo.Entity.Student;
import com.LibraryManagementSystem.demo.Repository.StudentRepository;
import com.LibraryManagementSystem.demo.dto.StudentBorrowSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }
    //add students//
    public Student addStudents(Student student){
        return studentRepository.save(student);
    }
    //list students//
    public Page<Student> listStudents(Pageable pageable){
        return studentRepository.findAll(pageable);
    }
    //update students//
    public Student updateStudents(Long studentId, Student student){
        Student stu=studentRepository.findById(studentId)
                .orElseThrow(()-> new RuntimeException("Student not found"));
        stu.setEmail(student.getEmail());
        return studentRepository.save(student);
    }
    // delete students by id//
    public void deleteStudent(Long studentId){
        studentRepository.findById(studentId)
                .orElseThrow(()->new RuntimeException("Student not found"));
        studentRepository.deleteById(studentId);
    }
    public List<Student> filternameandemail(String name, String email){
        if(name!= null){
            return studentRepository.findByname(name);
        }
        if(email != null){
            return studentRepository.findByemail(email);
        }
        return studentRepository.findAll();
    }
    public List <Student> findByCourse(String course){

        return studentRepository.findByCourse(course);
    }
    public List<StudentBorrowSummary> getBookSByCount(){
        return studentRepository.getBookSByCount();
    }


}
