package com.bartek.library.service.admin;

import com.bartek.library.model.Book;
import com.bartek.library.model.NewBookPurchaseRequest;
import com.bartek.library.repository.BookRepository;
import com.bartek.library.repository.NewBookPurchaseRequestRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AdminNewBookPurchaseRequestServiceTest {

    @Mock
    private NewBookPurchaseRequestRepository newBookPurchaseRequestRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AdminNewBookPurchaseRequestService adminNewBookPurchaseRequestService;

    @Test
    public void shouldSetApprovedToTrueByAcknowledgingPurchaseRequest() {
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

    @Test
    public void shouldReturnListOfTwoPurchaseRequests() {
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

        NewBookPurchaseRequest dummyPurchaseRequest2 =
                NewBookPurchaseRequest
                        .builder()
                        .dateOfRequest(dummyTime.plusDays(1))
                        .approved(false)
                        .author("Franek Frankoski")
                        .title("Jak sie bawic w Sopocie")
                        .category("Gry i zabawy")
                        .requestedBy("Bartek")
                        .build();
        //when
        when(newBookPurchaseRequestRepository.findAll()).thenReturn(Arrays.asList(dummyPurchaseRequest, dummyPurchaseRequest2));
        //then
        Assert.assertEquals(2, adminNewBookPurchaseRequestService.findAllPurchaseRequests().size());
        verify(newBookPurchaseRequestRepository, times(1)).findAll();
    }

}
