package au.com.wholesale.account.details.mapper;

import au.com.wholesale.account.details.domain.transaction.Transaction;
import au.com.wholesale.account.details.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {
    private TransactionDto from(Transaction transaction) {
        return TransactionDto.builder()
                .accountName(transaction.getAccountName())
                .accountNumber(transaction.getAccountNumber())
                .type(transaction.getType().name())
                .creditAmount(transaction.getCreditAmount())
                .currency(transaction.getCurrency().getCurrencyCode())
                .debitAmount(transaction.getDebitAmount())
                .valueDate(transaction.getValueDate())
                .transactionNarrative(transaction.getTransactionNarrative())
                .build();
    }

    public List<TransactionDto> from(List<Transaction> transactions) {
        return transactions.stream().map(this::from).collect(Collectors.toList());
    }

}
