package com.bartek.library.controller.admin;

import com.bartek.library.model.Book;
import com.bartek.library.service.admin.AdminBookService;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminBookController.class, secure = false)
@RunWith(SpringRunner.class)
public class AdminBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminBookService adminBookService;

    @Test
    public void shouldReturnOneBookBySavingBook() throws Exception {
        //given
        Book dummyBook = Book
                .builder()
                .author("Mieszko I")
                .title("Jak ochrzcic Polske")
                .category("Poradniki")
                .available(true)
                .build();

        String jsonResponse = "{\"id\":0,\"title\":\"Jak ochrzcic Polske\",\"author\":\"Mieszko I\",\"category\":\"Poradniki\",\"available\":true}";
        //when
        when(adminBookService.saveBook(any())).thenReturn(dummyBook);
        //then
        Assert.assertEquals(mockMvc.perform(post("/admin/book/save").contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(dummyBook)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString(), jsonResponse);

        verify(adminBookService, times(1)).saveBook(any());
    }

    @Test
    public void shouldReturnStatusHttp200ByDeleteBook() throws Exception {
        //verify
        mockMvc.perform(delete("/admin/book/delete?id=1"))
                .andExpect(status().isOk());

        verify(adminBookService, times(1)).deleteBookById(any());
    }
}
