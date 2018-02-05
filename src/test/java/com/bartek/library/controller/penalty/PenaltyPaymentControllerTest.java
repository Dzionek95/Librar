package com.bartek.library.controller.penalty;

import com.bartek.library.service.penalty.PenaltyPaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PenaltyPaymentController.class, secure = false)
@RunWith(SpringRunner.class)
public class PenaltyPaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PenaltyPaymentService penaltyPaymentService;

    @Test
    public void shouldReturnZeroAsAmountOfMoneyLeftToPay() throws Exception {
        //given
        double moneyToPayForPenalty = 0.0;
        //when
        when(penaltyPaymentService.payPenalty(anyDouble())).thenReturn(moneyToPayForPenalty);
        //then
        mockMvc.perform(post("/payment?moneyFromReaderForPenalty=4.3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", is(0.0)));
    }

}
