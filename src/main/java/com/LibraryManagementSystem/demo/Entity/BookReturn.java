package com.LibraryManagementSystem.demo.Entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class BookReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;
    @OneToOne
    private BookIssue bookIssue;
    private Instant returnDate;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Books books;

    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public BookIssue getBookIssue() {
        return bookIssue;
    }

    public void setBookIssue(BookIssue bookIssue) {
        this.bookIssue = bookIssue;
    }

    public Instant getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Instant returnDate) {
        this.returnDate = returnDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}
