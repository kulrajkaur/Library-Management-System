package com.LibraryManagementSystem.demo.Repository;

import com.LibraryManagementSystem.demo.Entity.Student;
import com.LibraryManagementSystem.demo.dto.StudentBorrowSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findByname(String name);
    List<Student> findByemail(String email);
    List <Student> findByCourse(String course);
    @Query("""
  SELECT new com.LibraryManagementSystem.demo.dto.StudentBorrowSummary(
   s.name,
   s.email,
   COUNT(bi)
  )
  From BookIssue bi
  JOIN bi.student s
  GROUP BY s.name, s.email
  HAVING COUNT(bi) > 2
""")
    List<StudentBorrowSummary> getBookSByCount();


}
