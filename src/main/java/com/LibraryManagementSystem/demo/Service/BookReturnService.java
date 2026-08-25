package com.LibraryManagementSystem.demo.Service;

import com.LibraryManagementSystem.demo.Entity.BookIssue;
import com.LibraryManagementSystem.demo.Entity.BookReturn;
import com.LibraryManagementSystem.demo.Entity.Books;
import com.LibraryManagementSystem.demo.Repository.BookIssueRepository;
import com.LibraryManagementSystem.demo.Repository.BookRepository;
import com.LibraryManagementSystem.demo.Repository.BookReturnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class BookReturnService {
    private final BookReturnRepository bookReturnRepository;
    private final BookIssueRepository bookIssueRepository;
    private final BookRepository bookRepository;
    public BookReturnService(BookReturnRepository bookReturnRepository,
                             BookIssueRepository bookIssueRepository,
                             BookRepository bookRepository){
        this.bookReturnRepository=bookReturnRepository;
        this.bookIssueRepository=bookIssueRepository;
        this.bookRepository=bookRepository;
    }
    @Transactional
    public void returnBook(Long issueId){
        BookIssue issue= bookIssueRepository.findById(issueId)
                .orElseThrow(()->new RuntimeException("Issue Record not found"));
        if("Returned".equals(issue.getStatus())){
            throw new RuntimeException("Book already returned");
        }
        Books book= issue.getBooks();
        book.setQuantity(book.getQuantity()+1);
        issue.setStatus("Returned");
        BookReturn bookReturn= new BookReturn();
        bookReturn.setBookIssue(issue);
        bookReturn.setReturnDate(Instant.now());
        bookReturnRepository.save(bookReturn);
    }
}
