package au.com.wholesale.account.details.service;

import au.com.wholesale.account.details.domain.transaction.Transaction;
import au.com.wholesale.account.details.domain.transaction.TransactionType;
import au.com.wholesale.account.details.dto.TransactionDto;
import au.com.wholesale.account.details.mapper.TransactionMapper;
import au.com.wholesale.account.details.repository.AccountRepository;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TransactionServiceTest {

    @Mock
    private AccountRepository mockRepository;
    @Mock
    private TransactionMapper mockMapper;
    private BDDSoftAssertions softly;

    private TransactionService transactionServiceUnderTest;

    @BeforeEach
    public void setUp() {
        softly = new BDDSoftAssertions();
        initMocks(this);
        transactionServiceUnderTest = new TransactionService(mockRepository, mockMapper);
    }

    @Test
    public void testGetTransactionDtos() {
        // Setup
        Long accountNumber = 0L;
        when(mockRepository.getTransactionsForAccount(anyLong()))
                .thenReturn(singletonList(new Transaction("accountNumber",
                                                          "accountName",
                                                          LocalDate.of(2019, Month.JULY, 26),
                                                          Currency.getInstance("AUD"), BigDecimal.ONE, BigDecimal.ONE,
                                                          TransactionType.DEBIT, "transactionNarrative")));


        when(mockMapper.from(any()))
                .thenReturn(singletonList(new TransactionDto("accountNumber", "accountName",
                                                             LocalDate.of(2019, Month.JULY, 26),
                                                             "AUD", BigDecimal.ONE, BigDecimal.ONE,
                                                             "debit", "transactionNarrative")));


        // Run the test
        List<TransactionDto> result = transactionServiceUnderTest.getTransactionDtos(accountNumber, 1L);

        // Verify the results
        softly.then(result).hasSize(1);
        softly.then(result).extracting("accountNumber").containsOnly("accountNumber");
        softly.then(result).extracting("accountName").containsOnly("accountName");
        softly.then(result).extracting("currency").containsOnly("AUD");
        softly.then(result).extracting("type").containsOnly("debit");
        softly.assertAll();
    }
}
