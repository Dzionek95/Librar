package com.bartek.library.repository.book;

import com.bartek.library.model.book.BookRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRentalRepository extends CrudRepository<BookRental, Long> {



}
