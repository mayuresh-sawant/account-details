package au.com.wholesale.account.details.service;

import au.com.wholesale.account.details.dto.AccountDto;
import au.com.wholesale.account.details.mapper.AccountMapper;
import au.com.wholesale.account.details.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    public List<AccountDto> getAccountDtos(Integer limit) {
        var accounts = repository.getAccounts().stream().limit(limit).collect(Collectors.toList());
        return mapper.from(accounts);
    }
}
