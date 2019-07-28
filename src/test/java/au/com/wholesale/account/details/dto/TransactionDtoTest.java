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
class TransactionDtoTest {


    @Value("classpath:transactionDto.json")
    Resource transDtoRes;

    @Autowired
    private JacksonTester<TransactionDto> json;


    private TransactionDto transactionDto;

    @Test
    public void serialisationTest() throws IOException {
        var given = TransactionDto.builder()
                .valueDate(LocalDate.now())
                .transactionNarrative("comments")
                .debitAmount(BigDecimal.ONE)
                .currency("AUD")
                .creditAmount(null)
                .type("debit")
                .accountName("test")
                .accountNumber("123")
                .build();
        assertThat(json.write(given)).isEqualToJson(transDtoRes);

    }


}
