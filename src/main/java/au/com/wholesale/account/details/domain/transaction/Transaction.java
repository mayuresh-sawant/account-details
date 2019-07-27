package au.com.wholesale.account.details.domain.transaction;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Value
@RequiredArgsConstructor
public class Transaction {
    private final String accountNumber;
    private final String accountName;
    private final LocalDate valueDate;
    private final Currency currency;
    private final BigDecimal debitAmount;
    private final BigDecimal creditAmount;
    private final TransactionType type;
    private final String transactionNarrative;
}
