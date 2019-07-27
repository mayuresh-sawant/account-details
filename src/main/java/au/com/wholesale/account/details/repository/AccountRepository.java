package au.com.wholesale.account.details.repository;

import au.com.wholesale.account.details.datastore.AccountStore;
import au.com.wholesale.account.details.domain.account.Account;
import au.com.wholesale.account.details.domain.transaction.Transaction;
import au.com.wholesale.account.details.exception.AccountNotFoundProblem;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AccountRepository {
    private static final int ACCOUNTS_LIMIT = 100;
    private static final int TRANS_LIMIT = 200;
    private final Map<Long, Account> accounts;

    public AccountRepository() {
        AccountStore accountStore = new AccountStore();
        accounts = new HashMap<>();

        var accountList = accountStore.generateAccounts(ACCOUNTS_LIMIT, TRANS_LIMIT);
        accountList.forEach(a -> accounts.put(a.getAccountNumber(), a));
    }

    public Collection<Account> getAccounts() {
        return List.copyOf(accounts.values());
    }

    public List<Transaction> getTransactionsForAccount(Long accountNumber) {
        Account account = Optional.ofNullable(accounts.get(accountNumber))
                .orElseThrow(() -> new AccountNotFoundProblem(accountNumber));
        return account.getTransactions();

    }
}
