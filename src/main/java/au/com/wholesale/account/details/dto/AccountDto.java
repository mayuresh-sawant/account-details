package au.com.wholesale.account.details.dto;

import au.com.wholesale.account.details.util.serializer.MoneySerializer;
import au.com.wholesale.account.details.util.serializer.date.AccountsDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@RequiredArgsConstructor
@Builder
public class AccountDto {
    private final Long accountNumber;
    private final String accountName;
    private final String accountType;
    @JsonSerialize(using = AccountsDateSerializer.class)
    private final LocalDate balanceDate;
    private final String currency;
    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal openingAvailableBalance;
}
