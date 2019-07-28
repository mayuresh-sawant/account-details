package au.com.wholesale.account.details.controller;

import au.com.wholesale.account.details.dto.TransactionDto;
import au.com.wholesale.account.details.exception.AccountNotFoundProblem;
import au.com.wholesale.account.details.service.TransactionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @MockBean
    TransactionService transactionService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;
    private BDDSoftAssertions softly;

    @BeforeEach
    public void setUp() {
        softly = new BDDSoftAssertions();
        initMocks(this);
    }

    @Test
    @DisplayName("Should return Transaction for a given valid account number")
    void shouldReturnTransactionForValidAccountNumber() throws Exception {

        //setup
        when(transactionService.getTransactionDtos(anyLong(), anyLong())).thenReturn(Arrays.asList(TransactionDto.builder()
                                                                                                           .valueDate(LocalDate
                                                                                                                              .now())
                                                                                                           .transactionNarrative("comments")
                                                                                                           .debitAmount(BigDecimal.ONE)
                                                                                                           .currency("AUD")
                                                                                                           .creditAmount(null)
                                                                                                           .type("debit")
                                                                                                           .accountName("test")
                                                                                                           .accountNumber("123")
                                                                                                           .build()));

        // Run the test
        var result = mockMvc.perform(get("/accounts/123/transactions"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();


        var response = result.getResponse();
        var accountDtos = mapper.readValue(response.getContentAsString(), JsonNode[].class);

        // Verify the results
        softly.then(response.getStatus()).isEqualTo(200);
        softly.then(accountDtos.length).isEqualTo(1);
        softly.then(accountDtos[0].get("transactionNarrative").asText()).isEqualTo("comments");
        softly.then(accountDtos[0].get("accountNumber").asText()).isEqualTo("123");
        softly.then(accountDtos[0].get("accountName").asText()).isEqualTo("test");
        softly.then(accountDtos[0].get("type").asText()).isEqualTo("debit");
        softly.then(accountDtos[0].get("currency").asText()).isEqualTo("AUD");
        softly.assertAll();
    }

    @Test
    @DisplayName("Should throw AccountNotFountException given invalid Account Number")
    void shouldThrowExceptionForInValidAccountNumber() throws Exception {

        //setup
        when(transactionService.getTransactionDtos(anyLong(), anyLong())).thenThrow(new AccountNotFoundProblem(123L));

        // Run the test and verify
        mockMvc.perform(get("/accounts/123/transactions"))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }
}
