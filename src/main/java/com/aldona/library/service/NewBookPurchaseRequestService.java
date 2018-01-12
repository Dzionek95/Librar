package com.aldona.library.service;

import com.aldona.library.model.Book;
import com.aldona.library.model.NewBookPurchaseRequest;
import com.aldona.library.repository.BookRepository;
import com.aldona.library.repository.NewBookPurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewBookPurchaseRequestService {

    private NewBookPurchaseRequestRepository newBookPurchaseRequestRepository;
    private BookRepository bookRepository;
    private SecurityUtilities securityUtilities;

    NewBookPurchaseRequestService(NewBookPurchaseRequestRepository newBookPurchaseRequestRepository,
                                  BookRepository bookRepository,
                                  SecurityUtilities securityUtilities) {
        this.newBookPurchaseRequestRepository = newBookPurchaseRequestRepository;
        this.bookRepository = bookRepository;
        this.securityUtilities = securityUtilities;
    }

    public NewBookPurchaseRequest createNewBookRequest(NewBookPurchaseRequest bookRequest) {

        if (checkIfBookIsAlreadyAvailableInLibrary(bookRequest)) {
            if (checkIfSuchRequestHasBeenAlreadyCreated(bookRequest)) {
                fillRequestWithAdditionalData(bookRequest);
                newBookPurchaseRequestRepository.save(bookRequest);
            } else {
                throw new IllegalArgumentException("Such request has been already made!");
            }
        } else {
            throw new IllegalArgumentException("Book is already library!");
        }

        return bookRequest;
    }


    private boolean checkIfBookIsAlreadyAvailableInLibrary(NewBookPurchaseRequest bookRequest) {
        List<Book> booksWithSameAuthor = bookRepository
                .findByAuthorIgnoreCaseContaining(bookRequest.getAuthor());

        List<Book> allBooksThatMatchBookRequest = booksWithSameAuthor
                .stream()
                .filter(bookToCheck -> bookToCheck.getTitle()
                        .equalsIgnoreCase(bookRequest.getTitle()))
                .collect(Collectors.toList());

        return allBooksThatMatchBookRequest.size() == 0;
    }

    private boolean checkIfSuchRequestHasBeenAlreadyCreated(NewBookPurchaseRequest bookRequest) {
        List<NewBookPurchaseRequest> allPurchaseRequests = findAllBookRequests();

        List<NewBookPurchaseRequest> allRequestsThatAreEqual = allPurchaseRequests
                .stream()
                .filter(newRequestToCheck -> newRequestToCheck.equals(bookRequest))
                .collect(Collectors.toList());

        return allRequestsThatAreEqual.size() == 0;
    }

    private List<NewBookPurchaseRequest> findAllBookRequests() {
        List<NewBookPurchaseRequest> allPurchaseRequests = new ArrayList<>();

        newBookPurchaseRequestRepository
                .findAll()
                .forEach(allPurchaseRequests::add);

        return allPurchaseRequests;
    }

    private void fillRequestWithAdditionalData(NewBookPurchaseRequest bookRequest) {
        bookRequest.setApproved(false);
        bookRequest.setDateOfRequest(LocalDateTime.now());
        bookRequest.setRequestedBy(securityUtilities.retrieveNameFromAuthentication());
    }
}
