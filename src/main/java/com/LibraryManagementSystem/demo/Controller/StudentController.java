package com.LibraryManagementSystem.demo.Controller;

import com.LibraryManagementSystem.demo.Entity.Student;
import com.LibraryManagementSystem.demo.Service.StudentService;
import com.LibraryManagementSystem.demo.dto.StudentBorrowSummary;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    //add student//
    @PostMapping("/add")
    public Student addStudents(@Valid @RequestBody Student student){
        return studentService.addStudents(student);
    }
    //list students//
    @GetMapping("/list/page")
    public Page<Student> listStudents(@RequestParam int page, @RequestParam int size){
        Pageable pageable= PageRequest.of(page, size);
        return studentService.listStudents(pageable);
    }
    //update students//
    @PutMapping("/update/{studentId}")
    public Student updateStudents(@PathVariable Long studentId, @RequestBody Student student){
        return studentService.updateStudents(studentId,student);
    }
    @GetMapping("/filter")
    public List<Student> filternameandemail(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String email){
        return studentService.filternameandemail(name, email);
    }
    @GetMapping("/by-course")
    public List <Student> findByCourse(String course){
        return studentService.findByCourse(course);
    }
    @GetMapping("/count")
    public List<StudentBorrowSummary> getBookSByCount(){
        return studentService.getBookSByCount();
    }
    //Delete student//
    @DeleteMapping("/delete/{studentId}")
    public void deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
    }
}
