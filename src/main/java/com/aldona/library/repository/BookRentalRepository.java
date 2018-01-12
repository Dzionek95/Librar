package com.aldona.library.repository;

import com.aldona.library.model.BookRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRentalRepository extends CrudRepository<BookRental, Long> {
}
