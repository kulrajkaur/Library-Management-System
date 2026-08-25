package com.LibraryManagementSystem.demo.dto;

public class StudentBorrowSummary {
    private String studentName;
    private String email;
    private Long totalBooks;
    public StudentBorrowSummary(String studentName, String email, Long totalBooks){
        this.studentName=studentName;
        this.email=email;
        this.totalBooks=totalBooks;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(Long totalBooks) {
        this.totalBooks = totalBooks;
    }
}
