package com.LibraryManagementSystem.demo.Repository;

import com.LibraryManagementSystem.demo.Entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Books,Long > {
    List<Books> findByBookNameIgnoreCase(String bookName);
    List<Books> findByAuthorIgnoreCase(String author);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Books b WHERE bookId= :bookId")
    Optional<Books> findBookByLock(@Param("bookId") Long bookId);



}
