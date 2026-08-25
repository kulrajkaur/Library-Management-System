package com.LibraryManagementSystem.demo.Controller;

import com.LibraryManagementSystem.demo.Service.BookReturnService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-return")
public class BookReturnController {
    private final BookReturnService bookReturnService;
    public BookReturnController(BookReturnService bookReturnService){
        this.bookReturnService=bookReturnService;
    }
    @PostMapping("/return")
    public void returnBook(@RequestParam Long issueId){
        bookReturnService.returnBook(issueId);
    }
}
