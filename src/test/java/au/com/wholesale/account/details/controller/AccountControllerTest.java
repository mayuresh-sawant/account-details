package au.com.wholesale.account.details.controller;

import au.com.wholesale.account.details.dto.AccountDto;
import au.com.wholesale.account.details.service.AccountService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    ObjectMapper mapper;
    @MockBean
    private AccountService mockAccountService;
    @Autowired
    private MockMvc mockMvc;
    private BDDSoftAssertions softly;

    @BeforeEach
    public void setUp() {
        softly = new BDDSoftAssertions();
        initMocks(this);
    }

    @Test
    public void testAccounts() throws Exception {
        // Setup
        Integer limit = 1;
        when(mockAccountService.getAccountDtos(anyInt())).thenReturn(Arrays.asList(AccountDto.builder()
                                                                                           .accountNumber(59157639L)
                                                                                           .accountName("GMCurrent349")
                                                                                           .accountType("CURRENT")
                                                                                           .balanceDate(LocalDate.now())
                                                                                           .currency("AUD")
                                                                                           .openingAvailableBalance(new BigDecimal("1123.7619"))
                                                                                           .build()));

        // Run the test
        var result = mockMvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        var response = result.getResponse();
        var accountDtos = mapper.readValue(response.getContentAsString(), JsonNode[].class);

        // Verify the results
        softly.then(response.getStatus()).isEqualTo(200);
        softly.then(accountDtos.length).isEqualTo(1);
        softly.then(accountDtos[0].get("accountNumber").asInt()).isEqualTo(59157639);
        softly.then(accountDtos[0].get("accountName").asText()).isEqualTo("GMCurrent349");
        softly.then(accountDtos[0].get("accountType").asText()).isEqualTo("CURRENT");
        softly.then(accountDtos[0].get("currency").asText()).isEqualTo("AUD");
        softly.assertAll();
    }
}
