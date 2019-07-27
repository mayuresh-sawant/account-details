package au.com.wholesale.account.details.service;

import au.com.wholesale.account.details.dto.TransactionDto;
import au.com.wholesale.account.details.mapper.TransactionMapper;
import au.com.wholesale.account.details.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountRepository repository;
    private final TransactionMapper mapper;

    public List<TransactionDto> getTransactionDtos(Long accountNumber, long limit) {
        var transactions = repository.getTransactionsForAccount(accountNumber)
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
        return mapper.from(transactions);
    }
}
