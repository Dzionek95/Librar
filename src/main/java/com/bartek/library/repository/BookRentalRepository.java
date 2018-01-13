package com.bartek.library.repository;

import com.bartek.library.model.BookRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRentalRepository extends CrudRepository<BookRental, Long> {
}
