package au.com.wholesale.account.details.repository;

import au.com.wholesale.account.details.domain.account.Account;
import au.com.wholesale.account.details.exception.AccountNotFoundProblem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountRepositoryTest {

    private AccountRepository accountRepositoryUnderTest;

    @BeforeEach
    public void setUp() {
        accountRepositoryUnderTest = new AccountRepository();
    }

    @Test
    public void testGetAccounts() {
        // Setup
        int expectedResult = 100;

        // Run the test
        Collection<Account> result = accountRepositoryUnderTest.getAccounts();

        // Verify the results
        assertEquals(expectedResult, result.size());
    }

    @Test
    public void testGetTransactionsForInvalidAccount() {
        // Setup
        Long accountNumber = 0L;

        // Run & verify the test
        Assertions.assertThrows(AccountNotFoundProblem.class, () -> accountRepositoryUnderTest.getTransactionsForAccount(accountNumber));
    }
}
