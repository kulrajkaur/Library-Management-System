package com.LibraryManagementSystem.demo.Repository;

import com.LibraryManagementSystem.demo.Entity.BookIssue;
import com.LibraryManagementSystem.demo.dto.IssuedBook;
import com.LibraryManagementSystem.demo.dto.StudentBorrowSummary;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    List<BookIssue> findByStatus(String status);
    @NativeQuery("""
     SELECT 
         b.book_name AS book_name,
         b.author AS book_author,
         s.name AS student_name,
         s.email AS student_email,
         bi.issue_date,
         bi.due_date
     FROM book_issue bi
     JOIN books b ON bi.books_book_id=b.book_id
     JOIN student  s ON bi.student_student_id= s.student_id
     WHERE s.student_id= :studentId
""")
    List<Map<String,Object>> findIssuedBooksByStudentId(@Param("studentId")Long studentId);

    @Query("""
     SELECT new com.LibraryManagementSystem.demo.dto.StudentBorrowSummary(
     s.name,
     s.email,
     COUNT(bi)
     )
     FROM BookIssue bi
     JOIN bi.student s
     WHERE s.studentId= :studentId
     GROUP BY s.name, s.email 
""")
    StudentBorrowSummary getBorrowSummary(@Param("studentId")Long studentId);

    @Query("""
     SELECT new com.LibraryManagementSystem.demo.dto.IssuedBook(
     b.bookName,
     s.name
     )
     From BookIssue bi
     JOIN bi.books b
     JOIN bi.student s
""")
    List <IssuedBook> getIssuedBookDetails();

    @Query("""
    SELECT bi
    FROM BookIssue bi
    WHERE bi.dueDate < CURRENT_TIMESTAMP
    AND bi.status = 'Borrowed'
""")
    List<BookIssue> findOverdueBooks();
}


