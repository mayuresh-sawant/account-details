package au.com.wholesale.account.details.datastore;

import au.com.wholesale.account.details.domain.account.Account;
import au.com.wholesale.account.details.domain.account.AccountType;
import au.com.wholesale.account.details.domain.transaction.Transaction;
import au.com.wholesale.account.details.domain.transaction.TransactionType;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class AccountStore {
    private static final int AT_MOST_DAYS = 300;
    private static final long MIN_AMOUNT = 10L;
    private static final long MAX_AMOUNT = 1000L;
    private static final long MIN_LEN_ACCOUNT_NUM = 10000000L;
    private static final long MAX_LEN_ACCOUNT_NUM = 99999999L;
    private static final int NUMBER_OF_DECIMALS = 4;
    private static final long MAX_OPENING_BAL = 10000L;
    private final Faker faker = new Faker();

    private static LocalDate convert(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public List<Account> generateAccounts(long accountsLimit, long transLimit) {
        return Stream.generate(() -> generateAccount(transLimit))
                .limit(accountsLimit)
                .collect(Collectors.toList());
    }

    private List<Transaction> generateTransactions(Account account, long limit) {
        return Stream.generate(() -> generateTransaction(account))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Transaction generateTransaction(Account account) {

        String num = faker.numerify("###-###-###");
        String accType = StringUtils.capitalize(account.getAccountType().getType().toLowerCase());
        var date = java.sql.Date.valueOf(account.getBalanceDate());
        Date valueDate = faker.date().past(AT_MOST_DAYS, TimeUnit.DAYS, date);
        Currency currency = account.getCurrency();
        var amount = faker.number().randomDouble(NUMBER_OF_DECIMALS, MIN_AMOUNT, MAX_AMOUNT);
        TransactionType transactionType;
        String accountName = String.format("%s Account", accType);
        if (faker.bool().bool()) {
            transactionType = TransactionType.CREDIT;
            return new Transaction(num, accountName, convert(valueDate), currency, null,
                                   BigDecimal.valueOf(amount), transactionType, generateNarrative(transactionType));
        } else {
            transactionType = TransactionType.DEBIT;
            return new Transaction(num, accountName, convert(valueDate), currency, BigDecimal.valueOf(amount),
                                   null, transactionType, generateNarrative(transactionType));
        }
    }

    private Account generateAccount(long transactionCount) {
        long accountNumber = faker.number().numberBetween(MIN_LEN_ACCOUNT_NUM, MAX_LEN_ACCOUNT_NUM);

        var fakerCurrency = faker.currency();
        Currency currency = Currency.getInstance(fakerCurrency.code());
        String accountType = faker.regexify("(Saving|Current)");

        String accountName = generateAccountName(fakerCurrency.code().substring(0, 2), accountType);

        AccountType type = AccountType.valueOf(accountType.toUpperCase());

        Date balanceDate = faker.date().past(AT_MOST_DAYS, TimeUnit.DAYS);
        LocalDate date = convert(balanceDate);

        var openingBalance = faker.number().randomDouble(NUMBER_OF_DECIMALS, MIN_AMOUNT, MAX_OPENING_BAL);

        var account = new Account(accountNumber, accountName, type, date,
                                  currency, BigDecimal.valueOf(openingBalance), new ArrayList<>());

        account.getTransactions().addAll(generateTransactions(account, transactionCount));
        return account;
    }

    private String generateAccountName(String code, String accountType) {
        return code.substring(0, 2) + accountType + faker.regexify("[0-9]{3}");
    }

    private String generateNarrative(TransactionType transactionType) {
        String iban = faker.finance().iban();
        String companyName = faker.company().name();
        if (transactionType == TransactionType.DEBIT) {
            return String.format("TRANSFER RefId: %s FROM: %s", iban, companyName);
        } else {
            return String.format("PAYMENT RefId:%s TO: %s", iban, companyName);
        }

    }
}
