package com.bartek.library.repository.book;

import com.bartek.library.model.book.OrdersQueue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdersQueueRepository extends CrudRepository<OrdersQueue, Long> {
    @Query(value = "Select * from ORDERS_QUEUE,BOOK " +
            "where ORDERS_QUEUE.QUEUE_TO_BOOK_ID = BOOK.ID " +
            "AND BOOK.ID =?1 ", nativeQuery = true)
    List<OrdersQueue> findAllPeopleInQueue(Long idOfOrder);

}
