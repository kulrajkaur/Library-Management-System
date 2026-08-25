package com.LibraryManagementSystem.demo.Controller;

import com.LibraryManagementSystem.demo.Entity.BookIssue;
import com.LibraryManagementSystem.demo.Entity.Books;
import com.LibraryManagementSystem.demo.Service.BookIssueService;
import com.LibraryManagementSystem.demo.Service.BookService;
import com.LibraryManagementSystem.demo.dto.IssuedBook;
import com.LibraryManagementSystem.demo.dto.StudentBorrowSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book-issue")
public class BookIssueController {
    private final BookIssueService bookIssueService;
    private final BookService bookService;
    public BookIssueController(BookIssueService bookIssueService, BookService bookService){
        this.bookIssueService=bookIssueService;
        this.bookService=bookService;
    }
    @PostMapping("/issue")
    public void issueBook(@RequestParam Long bookId, @RequestParam Long studentId){
        bookIssueService.issueBook(bookId, studentId);
    }
    @GetMapping("/list")
    public List<BookIssue> findByStatus(String status){
        return bookIssueService.findByStatus(status);
    }
    @GetMapping("/detail-issued/{studentId}")
    public List<Map<String,Object>> findIssuedBooksByStudentId(@PathVariable Long studentId){
        return bookIssueService.findIssuedBooksByStudentId(studentId);
    }
    @GetMapping("/total-books/{studentId}")
    public StudentBorrowSummary getBorrowSummary(@PathVariable Long studentId){
        return bookIssueService.getBorrowSummary(studentId);
    }
    @GetMapping("/details")
    public List<IssuedBook> getIssuedBookDetails(){
        return bookIssueService.getIssuedBookDetails();
    }
    @GetMapping("/overdue")
    public List<BookIssue> findOverdueBooks(){
        return bookIssueService.findOverdueBooks();
    }
    @GetMapping("/lock/{bookId}")
    public Books findBookByLock(@PathVariable Long bookId){
        return bookIssueService.findBookByLock(bookId);
    }
}
