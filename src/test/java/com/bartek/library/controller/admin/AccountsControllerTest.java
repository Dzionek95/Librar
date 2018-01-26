package com.bartek.library.controller.admin;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.service.admin.AccountsService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountsController.class, secure = false)
@RunWith(SpringRunner.class)
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsService accountsService;

    @Test
    public void shouldReturnOneAccountByActionCreateAccount() throws Exception {
        //given
        Account dummyAccount = createAccountData();
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResponse = "{\"id\":0,\"username\":\"dummylogin\",\"password\":\"dummypassword\",\"role\":\"ROLE_ADMIN\",\"enabled\":true}";

        //when
        when(accountsService.createAccount(any())).thenReturn(dummyAccount);

        //then
        Assert.assertEquals(expectedResponse,
                mockMvc.perform(post("/admin/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dummyAccount)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse().getContentAsString());

        verify(accountsService, times(1)).createAccount(any());
    }

    @Test
    public void shouldReturnHttpResponse200ByActionDeleteAccount() throws Exception {
        //verify
        mockMvc.perform(delete("/admin/accounts/delete?id=1"))
                .andExpect(status().isOk());


        verify(accountsService, times(1)).deleteAccount(any());
    }

    @Test
    public void shouldReturnOneAccountThatHasBeenUpdated() throws Exception {
        //given
        Account dummyAccount = createAccountData();
        String expectedResponse = "{\"id\":0,\"username\":\"dummylogin\",\"password\":\"dummypassword\",\"role\":\"ROLE_ADMIN\",\"enabled\":true}";

        //when
        when(accountsService.updateAccount(any())).thenReturn(dummyAccount);
        //then
        Assert.assertEquals(expectedResponse, mockMvc.perform(put("/admin/accounts/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(dummyAccount)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        verify(accountsService, times(1)).updateAccount(any());
    }


    private Account createAccountData() {
        return Account
                .builder()
                .username("dummylogin")
                .password("dummypassword")
                .role(Role.ROLE_ADMIN)
                .enabled(true)
                .build();
    }

}
