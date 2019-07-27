package au.com.wholesale.account.details.domain.account;

import au.com.wholesale.account.details.domain.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Value
@RequiredArgsConstructor
public class Account {
    private final Long accountNumber;
    private final String accountName;
    private final AccountType accountType;
    private final LocalDate balanceDate;
    private final Currency currency;
    private final BigDecimal openingAvailableBalance;
    private final List<Transaction> transactions;
}
