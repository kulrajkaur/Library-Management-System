package com.LibraryManagementSystem.demo.Service;
import com.LibraryManagementSystem.demo.Entity.BookIssue;
import com.LibraryManagementSystem.demo.Entity.Books;
import com.LibraryManagementSystem.demo.Entity.Student;
import com.LibraryManagementSystem.demo.Repository.BookIssueRepository;
import com.LibraryManagementSystem.demo.Repository.BookRepository;
import com.LibraryManagementSystem.demo.Repository.StudentRepository;
import com.LibraryManagementSystem.demo.dto.IssuedBook;
import com.LibraryManagementSystem.demo.dto.StudentBorrowSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class BookIssueService {
    private final BookIssueRepository bookIssueRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final AtomicInteger issueRequestCount = new AtomicInteger(0);

    public BookIssueService(BookIssueRepository bookIssueRepository, BookRepository bookRepository, StudentRepository studentRepository) {
        this.bookIssueRepository = bookIssueRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void issueBook(Long bookId, Long studentId) {
        int requestNumber= issueRequestCount.incrementAndGet();
        System.out.println("Book issue request Number" +requestNumber);
        Optional<Books> booksOptional = bookRepository.findById(bookId);
        Optional<Student> studentsOptional = studentRepository.findById(studentId);
        if (booksOptional.isEmpty() && studentsOptional.isEmpty()) {
            throw new RuntimeException("Book and Student not found");
        }
        if (booksOptional.isEmpty()) {
            throw new RuntimeException("Book not found");
        }
        if (studentsOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        Books book = booksOptional.get();
        Student student = studentsOptional.get();
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is out of stock");
        }
        book.setQuantity(book.getQuantity() - 1);
        BookIssue issue = new BookIssue();
        issue.setBooks(book);
        issue.setStudent(student);
        issue.setStatus("Borrowed");
        Instant issueDate = Instant.now();
        Instant dueDate = issueDate.plus(14, ChronoUnit.DAYS);
        issue.setIssueDate(issueDate);
        issue.setDueDate(dueDate);
        bookIssueRepository.save(issue);
    }

    public List<BookIssue> findByStatus(String status) {
        return bookIssueRepository.findByStatus(status);
    }

    public List<Map<String, Object>> findIssuedBooksByStudentId(Long studentId) {
        return bookIssueRepository.findIssuedBooksByStudentId(studentId);
    }

    public StudentBorrowSummary getBorrowSummary(Long studentId) {
        return bookIssueRepository.getBorrowSummary(studentId);
    }

    public List<IssuedBook> getIssuedBookDetails() {
        return bookIssueRepository.getIssuedBookDetails();
    }

    public List<BookIssue> findOverdueBooks() {
        return bookIssueRepository.findOverdueBooks();
    }

    @Transactional
    public Books findBookByLock(Long bookId) {

        Books book = bookRepository.findBookByLock(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return book;
    }
}