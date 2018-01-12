package com.aldona.library.service.admin;

import com.aldona.library.model.Book;
import com.aldona.library.model.NewBookPurchaseRequest;
import com.aldona.library.repository.BookRepository;
import com.aldona.library.repository.NewBookPurchaseRequestRepository;
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

    public NewBookPurchaseRequest acknowledgePurchaseRequest(Long id) {
        NewBookPurchaseRequest newBookPurchaseRequest = newBookPurchaseRequestRepository.findOne(id);

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
