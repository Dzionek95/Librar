package com.aldona.library.configuration;

import com.aldona.library.model.Book;
import com.aldona.library.model.accounts.Accounts;
import com.aldona.library.model.accounts.Role;
import com.aldona.library.repository.BookRepository;
import com.aldona.library.repository.admin.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.InitBinder;

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
            accountRepository.save(Accounts
                    .builder()
                    .username("Aldona")
                    .password("Aldona")
                    .role(Role.ROLE_READER)
                    .enabled(true)
                    .build());

            accountRepository.save(Accounts
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

