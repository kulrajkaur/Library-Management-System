package com.LibraryManagementSystem.demo.dto;

public class IssuedBook {
    private String bookName;
    private String studentName;
    public IssuedBook(String bookName, String studentName){
        this.bookName=bookName;
        this.studentName=studentName;

    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
