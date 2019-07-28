package au.com.wholesale.account.details.service;

import au.com.wholesale.account.details.domain.account.Account;
import au.com.wholesale.account.details.domain.account.AccountType;
import au.com.wholesale.account.details.dto.AccountDto;
import au.com.wholesale.account.details.mapper.AccountMapper;
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

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceTest {

    @Mock
    private AccountRepository mockRepository;
    @Mock
    private AccountMapper mockMapper;

    private AccountService accountServiceUnderTest;
    private BDDSoftAssertions softly;

    @BeforeEach
    public void setUp() {
        softly = new BDDSoftAssertions();
        initMocks(this);
        accountServiceUnderTest = new AccountService(mockRepository, mockMapper);
    }

    @Test
    public void testGetAccountDtos() {
        // Setup
        Integer limit = 1;
        when(mockRepository.getAccounts()).thenReturn(singletonList(new Account(100L, "accountName",
                                                                                AccountType.SAVING, LocalDate
                                                                                        .of(2019, Month.JULY, 26),
                                                                                Currency.getInstance("AUD"), BigDecimal.ONE, emptyList())));

        when(mockMapper.from(any())).thenReturn(singletonList(new AccountDto(100L, "accountName",
                                                                             "accountType", LocalDate
                                                                                     .of(2019, Month.JULY, 26),
                                                                             "AUD", BigDecimal.ONE)));
        // Run the test
        List<AccountDto> result = accountServiceUnderTest.getAccountDtos(limit);

        // Verify the results
        softly.then(result).hasSize(1);
        softly.then(result).extracting("currency").containsOnly("AUD");
        softly.then(result).extracting("accountName").containsOnly("accountName");
        softly.then(result).extracting("accountNumber").containsOnly(100L);
        softly.assertAll();

    }
}
