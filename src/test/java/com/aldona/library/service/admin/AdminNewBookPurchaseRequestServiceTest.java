package com.aldona.library.service.admin;

import com.aldona.library.model.Book;
import com.aldona.library.model.NewBookPurchaseRequest;
import com.aldona.library.repository.BookRepository;
import com.aldona.library.repository.NewBookPurchaseRequestRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Bartlomiej Janik
 * @since 1/10/2018
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminNewBookPurchaseRequestServiceTest {

    @Mock
    private NewBookPurchaseRequestRepository newBookPurchaseRequestRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AdminNewBookPurchaseRequestService adminNewBookPurchaseRequestService;

    @Test
    public void shouldSetApprovedToTrueByAcknowledgingPurchaseRequest(){
        //given

        LocalDateTime dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        NewBookPurchaseRequest dummyPurchaseRequest =
                NewBookPurchaseRequest
                        .builder()
                        .dateOfRequest(dummyTime)
                        .approved(false)
                        .author("Adam Malysz")
                        .title("Skoki narciarskie na 100%")
                        .category("Sport")
                        .requestedBy("Zbyszek")
                        .build();

        //when
        when(newBookPurchaseRequestRepository.findOne(any())).thenReturn(dummyPurchaseRequest);
        //then
        Assert.assertTrue(adminNewBookPurchaseRequestService.acknowledgePurchaseRequest(any()).isApproved());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

}
