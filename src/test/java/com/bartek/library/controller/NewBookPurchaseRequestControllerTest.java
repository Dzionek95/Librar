package com.bartek.library.controller;

import com.bartek.library.model.NewBookPurchaseRequest;
import com.bartek.library.service.NewBookPurchaseRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = NewBookPurchaseRequestController.class, secure = false)
@RunWith(SpringRunner.class)
public class NewBookPurchaseRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewBookPurchaseRequestService newBookPurchaseRequestService;

    @Test
    public void shouldReturnOneBookByCreateNewBookRequest() throws Exception {
        //given
        LocalDateTime dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        //TODO find out why I cannot pass dummy time
        NewBookPurchaseRequest dummyPurchaseRequest = NewBookPurchaseRequest
                .builder()
                .approved(false)
                .author("Adam Malysz")
                .title("Jak skakac wysoko?")
                .category("Poradnik")
                .requestedBy("Zbyszek")
                .build();

        String expectedResponse = "{\"id\":0,\"title\":\"Jak skakac wysoko?\",\"author\":\"Adam Malysz\",\"category\":\"Poradnik\",\"requestedBy\":\"Zbyszek\",\"dateOfRequest\":null,\"approved\":false}";
        //when
        when(newBookPurchaseRequestService.createNewBookRequest(any())).thenReturn(dummyPurchaseRequest);
        //then
        Assert.assertEquals(expectedResponse, mockMvc.perform(post("/purchase/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(dummyPurchaseRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }

}
