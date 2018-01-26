package com.bartek.library.model.notifications;

import com.bartek.library.model.book.Book;
import com.bartek.library.model.accounts.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Account account;
    @OneToOne
    private Book book;
    private double amountOfMoney;

}
