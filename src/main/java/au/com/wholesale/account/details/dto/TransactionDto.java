package au.com.wholesale.account.details.dto;

import au.com.wholesale.account.details.util.serializer.MoneySerializer;
import au.com.wholesale.account.details.util.serializer.date.ValueDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@RequiredArgsConstructor
@Builder
public class TransactionDto {
    private final String accountNumber;
    private final String accountName;
    @JsonSerialize(using = ValueDateSerializer.class)
    private final LocalDate valueDate;
    private final String currency;
    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal debitAmount;
    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal creditAmount;
    private final String type;
    private final String transactionNarrative;
}
