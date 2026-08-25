package com.LibraryManagementSystem.demo.Repository;

import com.LibraryManagementSystem.demo.Entity.BookReturn;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BookReturnRepository extends JpaRepository<BookReturn,Long> {
}
