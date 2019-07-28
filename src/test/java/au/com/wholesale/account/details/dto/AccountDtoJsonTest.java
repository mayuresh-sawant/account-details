package au.com.wholesale.account.details.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@ExtendWith(SpringExtension.class)
public class AccountDtoJsonTest {

    @Value("classpath:accountDto.json")
    Resource accountDtoRes;

    @Autowired
    private JacksonTester<AccountDto> json;


    private AccountDto accountDtoUnderTest;

    @Test
    public void serialisationTest() throws IOException {
        var given = AccountDto.builder()
                .accountNumber(59157639L)
                .accountName("GMCurrent349")
                .accountType("CURRENT")
                .balanceDate(LocalDate.now())
                .currency("AUD")
                .openingAvailableBalance(new BigDecimal("1123.7619"))
                .build();
        assertThat(json.write(given)).isEqualToJson(accountDtoRes);

    }


}
