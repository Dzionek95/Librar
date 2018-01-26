package com.bartek.library.configuration;

import com.bartek.library.model.book.Book;
import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.repository.book.BookRepository;
import com.bartek.library.repository.admin.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class InitialData {

    private BookRepository bookRepository;
    private AccountRepository accountRepository;

    @Autowired
    InitialData(BookRepository bookRepository,
                AccountRepository accountRepository) {
        this.bookRepository = bookRepository;
        this.accountRepository = accountRepository;
    }

    @Bean
    InitializingBean persistUserToDb() {
        log.info("Persisted user data to database");
        return () -> {
            accountRepository.save(Account
                    .builder()
                    .username("Bartek")
                    .password("Bartek")
                    .role(Role.ROLE_READER)
                    .enabled(true)
                    .build());

            accountRepository.save(Account
                    .builder()
                    .username("Admin")
                    .password("Admin")
                    .role(Role.ROLE_ADMIN)
                    .enabled(true)
                    .build());

        };
    }

    @Bean
    InitializingBean persistBooksToDb() {
        log.info("Persisted book data to database");
        return () -> {
            bookRepository.save(Book
                    .builder()
                    .author("Henryk Sienkiewicz")
                    .title("Krzyżacy")
                    .category("powieść historyczna")
                    .available(true)
                    .build());
            bookRepository.save(Book
                    .builder()
                    .author("Zbigniew Nienacki")
                    .title("Nowe przygody Pana Samochodzika")
                    .category("powieść dla młodzieży")
                    .available(true)
                    .build());
            bookRepository.save(Book
                    .builder()
                    .author("Henryk Sienkiewicz")
                    .title("Ogniem i mieczem")
                    .category("powieść historyczna")
                    .available(true)
                    .build());
            bookRepository.save(Book
                    .builder()
                    .author("Bolesław Prus")
                    .title("Lalka")
                    .category("powieść społeczno-obyczajowa")
                    .available(true)
                    .build());
            bookRepository.save(Book
                    .builder()
                    .author("Fiodor Dostojewski")
                    .title("Zbrodnia i kara")
                    .category("powieść historyczna")
                    .available(true)
                    .build());
            bookRepository.save(Book
                    .builder()
                    .author("Adam Mickiewicz")
                    .title("Pan Tadeusz")
                    .category("poemat epicki")
                    .available(true)
                    .build());
        };
    }
}

