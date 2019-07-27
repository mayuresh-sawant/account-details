package au.com.wholesale.account.details.mapper;

import au.com.wholesale.account.details.domain.account.Account;
import au.com.wholesale.account.details.dto.AccountDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    private AccountDto from(Account account) {
        return AccountDto.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType().getType())
                .balanceDate(account.getBalanceDate())
                .currency(account.getCurrency().getCurrencyCode())
                .openingAvailableBalance(account.getOpeningAvailableBalance())
                .build();
    }

    public List<AccountDto> from(List<Account> accounts) {
        return accounts.stream().map(this::from).collect(Collectors.toList());
    }
}
