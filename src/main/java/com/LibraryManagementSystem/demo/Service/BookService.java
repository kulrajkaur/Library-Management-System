package com.LibraryManagementSystem.demo.Service;

import com.LibraryManagementSystem.demo.Entity.Books;
import com.LibraryManagementSystem.demo.Repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    //add books//
    public Books addBooks(Books books){
        return bookRepository.save(books);
    }
    //list books//
    public Page<Books> listBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }
    //update books//
    public Books updateBooks(Long userId,Books books){
        Books boo=bookRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Book not found"));
        boo.setBookName(books.getBookName());
        boo.setAuthor(books.getAuthor());
        boo.setQuantity(books.getQuantity());
        boo.setPublisher(books.getPublisher());
        return bookRepository.save(books);
    }
    public List <Books> filterBooks(String bookName, String author) {
        if (bookName != null) {
            return bookRepository.findByBookNameIgnoreCase(bookName);
        }
        if (author != null) {
            return bookRepository.findByAuthorIgnoreCase(author);
        }
        return bookRepository.findAll();
    }
    //delete book//
    public void deleteBook(Long bookId){
        bookRepository.findById(bookId)
                .orElseThrow(()-> new RuntimeException("Book not found"));
        bookRepository.deleteById(bookId);
    }
}
