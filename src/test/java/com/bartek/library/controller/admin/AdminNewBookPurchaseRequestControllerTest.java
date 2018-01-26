package com.bartek.library.controller.admin;

import com.bartek.library.model.book.NewBookPurchaseRequest;
import com.bartek.library.service.admin.AdminNewBookPurchaseRequestService;
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
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Bartlomiej Janik
 * @since 1/15/2018
 */
@WebMvcTest(controllers = AdminNewBookPurchaseRequestController.class, secure = false)
@RunWith(SpringRunner.class)
public class AdminNewBookPurchaseRequestControllerTest {

    private final static LocalDateTime dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminNewBookPurchaseRequestService adminNewBookPurchaseRequestService;

    @Test
    public void shouldReturnListOfTwoNewBookPurchaseRequestByFindAllOperation() throws Exception {
        //given
        NewBookPurchaseRequest dummyNewBookPurchaseRequest = createBookPurchaseData();
        NewBookPurchaseRequest dummyNewBookPurchaseRequest2 = createBookPurchaseData();
        dummyNewBookPurchaseRequest2.setApproved(true);
        //when
        when(adminNewBookPurchaseRequestService.findAllPurchaseRequests()).thenReturn(Arrays.asList(dummyNewBookPurchaseRequest, dummyNewBookPurchaseRequest2));
        //then
        mockMvc.perform(get("/admin/purchase/display"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].approved", is(false)))
                .andExpect(jsonPath("$[0].author", is("Adam Malysz")))
                .andExpect(jsonPath("$[0].dateOfRequest", is("2018-01-10 20:59:42")))
                .andExpect(jsonPath("$[0].title", is("Jak skakac wysoko?")))
                .andExpect(jsonPath("$[0].category", is("Poradnik")))
                .andExpect(jsonPath("$[0].requestedBy", is("Zbyszek")))
                .andExpect(jsonPath("$[1].approved", is(true)));

        verify(adminNewBookPurchaseRequestService, times(1)).findAllPurchaseRequests();
    }

    @Test
    public void shouldReturnOneNewBookPurchaseRequestByAcknowledge() throws Exception {
        //given
        NewBookPurchaseRequest dummyNewBookPurchaseRequest = createBookPurchaseData();
        String response = "{\"id\":0,\"title\":\"Jak skakac wysoko?\",\"author\":\"Adam Malysz\",\"category\":\"Poradnik\",\"requestedBy\":\"Zbyszek\",\"dateOfRequest\":\"2018-01-10 20:59:42\",\"approved\":false}";
        //when
        when(adminNewBookPurchaseRequestService.acknowledgePurchaseRequest(anyLong())).thenReturn(dummyNewBookPurchaseRequest);
        //then
        Assert.assertEquals(response, mockMvc.perform(post("/admin/purchase/acknowledge?id=1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(dummyNewBookPurchaseRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }


    private NewBookPurchaseRequest createBookPurchaseData() {
        return NewBookPurchaseRequest
                .builder()
                .approved(false)
                .author("Adam Malysz")
                .dateOfRequest(dummyTime)
                .title("Jak skakac wysoko?")
                .category("Poradnik")
                .requestedBy("Zbyszek")
                .build();
    }


}
