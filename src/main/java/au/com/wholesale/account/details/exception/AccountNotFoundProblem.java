package au.com.wholesale.account.details.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AccountNotFoundProblem extends AbstractThrowableProblem {
    public AccountNotFoundProblem(Long accountNumber) {
        super(
                null,
                "Account Not found",
                Status.BAD_REQUEST,
                String.format("Account '%s' not found", accountNumber));
    }
}
