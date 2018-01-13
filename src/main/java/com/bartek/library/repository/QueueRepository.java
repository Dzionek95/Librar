package com.bartek.library.repository;

import com.bartek.library.model.Queue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QueueRepository extends CrudRepository<Queue, Long> {
    @Query(value = "Select * from QUEUE,BOOK where QUEUE.QUEUE_TO_BOOK_ID = BOOK.ID AND BOOK.ID =?1 ", nativeQuery = true)
    List<Queue> findAllPeopleInQueue(Long Id);
}
