package com.bartek.library.model.book;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.book.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id","dateOfRental","returnDate"})
@Setter
@Getter
@Data
public class BookRental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Account account;
    @OneToOne
    private Book book;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfRental;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime returnDate;

}
