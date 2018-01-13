package com.bartek.library.service.admin;

import com.bartek.library.model.Book;
import com.bartek.library.model.NewBookPurchaseRequest;
import com.bartek.library.repository.BookRepository;
import com.bartek.library.repository.NewBookPurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminNewBookPurchaseRequestService {

    private NewBookPurchaseRequestRepository newBookPurchaseRequestRepository;
    private BookRepository bookRepository;

    AdminNewBookPurchaseRequestService(NewBookPurchaseRequestRepository newBookPurchaseRequestRepository,
                                       BookRepository bookRepository) {
        this.newBookPurchaseRequestRepository = newBookPurchaseRequestRepository;
        this.bookRepository = bookRepository;
    }

    public List<NewBookPurchaseRequest> findAllPurchaseRequests() {
        List<NewBookPurchaseRequest> allPurchaseRequests = new ArrayList<>();

        newBookPurchaseRequestRepository
                .findAll()
                .forEach(allPurchaseRequests::add);

        return allPurchaseRequests;
    }

    public NewBookPurchaseRequest acknowledgePurchaseRequest(Long idOfPurchaseRequest) {
        NewBookPurchaseRequest newBookPurchaseRequest = newBookPurchaseRequestRepository.findOne(idOfPurchaseRequest);

        saveNewBookToDb(newBookPurchaseRequest);
        newBookPurchaseRequest.setApproved(true);

        return newBookPurchaseRequest;
    }

    private void saveNewBookToDb(NewBookPurchaseRequest newBookPurchaseRequest) {
        Book book = Book
                .builder()
                .title(newBookPurchaseRequest.getTitle())
                .author(newBookPurchaseRequest.getAuthor())
                .available(true)
                .category(newBookPurchaseRequest.getCategory())
                .build();
        bookRepository.save(book);
    }
}
