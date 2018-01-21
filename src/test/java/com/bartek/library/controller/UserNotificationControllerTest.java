package com.bartek.library.controller;

import com.bartek.library.model.UserNotification;
import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.service.UsersNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserNotificationController.class, secure = false)
@RunWith(SpringRunner.class)
public class UserNotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersNotificationService usersNotificationService;

    @InjectMocks
    private UserNotificationController userNotificationController;

    @Test
    public void shouldReturnListOfOneUserNotification() throws Exception {
        //given

        LocalDateTime dummyTime =
                LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Account dummyAccount = Account
                .builder()
                .username("dummylogin")
                .password("dummypassword")
                .role(Role.ROLE_ADMIN)
                .build();

        UserNotification dummyNotification = UserNotification
                .builder()
                .message("Message")
                .account(dummyAccount)
                .timeOfCreationOfNotification(dummyTime)
                .build();

        String accountJsonResponse = "{id=0, username=dummylogin, password=dummypassword, role=ROLE_ADMIN, enabled=false}";

        //when
        when(usersNotificationService.getUsersAllNotifications())
                .thenReturn(Collections.singletonList(dummyNotification));
        //then
        mockMvc.perform(get("/notifications/display"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Message")))
                .andExpect(jsonPath("$[0].timeOfCreationOfNotification", is("2018-01-10 20:59:42")))
                .andExpect(jsonPath("$[0].account.username", is("dummylogin")))
                .andExpect(jsonPath("$[0].account.password", is("dummypassword")))
                .andExpect(jsonPath("$[0].account.role", is("ROLE_ADMIN")));

        verify(usersNotificationService, times(1)).getUsersAllNotifications();
    }
}

