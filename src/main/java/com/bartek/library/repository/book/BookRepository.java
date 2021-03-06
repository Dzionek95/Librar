package com.bartek.library.repository.book;

import com.bartek.library.model.book.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByAuthorIgnoreCaseContaining(String author);

    List<Book> findByCategoryIgnoreCaseContaining(String category);

    List<Book> findByTitleIgnoreCaseContaining(String title);

}
