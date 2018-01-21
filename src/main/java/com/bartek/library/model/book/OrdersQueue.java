package com.bartek.library.model.book;

import com.bartek.library.model.book.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrdersQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeOfJoiningQueue;
    @OneToOne
    private Book queueToBook;

}
