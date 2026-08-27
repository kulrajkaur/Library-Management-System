package com.LibraryManagementSystem.demo.Controller;

import com.LibraryManagementSystem.demo.Entity.Books;
import com.LibraryManagementSystem.demo.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService=bookService;
    }
    @PostMapping("/add")
    public Books addBooks(@Valid @RequestBody Books books){
        return bookService.addBooks(books);
    }
    @GetMapping("/list/page")
    public Page<Books> listBooks(@RequestParam int page, @RequestParam int size){
        Pageable pageable= PageRequest.of(page, size);
        return bookService.listBooks(pageable);
    }
    @PutMapping("/update/{userId}")
    public Books updateBooks(@PathVariable Long userId,@RequestBody Books books){
        return bookService.updateBooks(userId, books);
    }
    @GetMapping("/filters")
    public List<Books> filterBooks(@RequestParam(required = false) String bookName,
                                   @RequestParam(required=false) String author){
        return bookService.filterBooks(bookName,author);
    }
    @GetMapping("/search/book/{author}")
    public List <Books> findBookByAuthor(@PathVariable String author){
        return bookService.findBookByAuthor(author);
    }
    @GetMapping("/searchByBookTitle/{bookName}")
    public List <Books> findBookByTitle(@PathVariable String bookName){
        return bookService.findBookByTitle(bookName);
    }
    @DeleteMapping("/delete/{userId}")
    public void deleteBook(@PathVariable Long userId){
        bookService.deleteBook(userId);
    }
}
