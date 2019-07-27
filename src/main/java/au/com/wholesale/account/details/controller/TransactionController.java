package au.com.wholesale.account.details.controller;

import au.com.wholesale.account.details.dto.TransactionDto;
import au.com.wholesale.account.details.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(value = "Transaction API", tags = {"Transactions"})
public class TransactionController {
    private final TransactionService transactionService;

    @ApiOperation(
            value = "Get Transaction for account",
            tags = {"Transactions"},
            notes = "Retrieve Transactions for a Account as json for customer",
            httpMethod = "GET",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Transactions as Json"),
            @ApiResponse(code = 400, message = "Invalid Account number"),
            @ApiResponse(code = 404, message = "Account not found"),
            @ApiResponse(code = 500, message = "Failed to retrieve Transaction")
    })
    @GetMapping(value = "/accounts/{accountNumber}/transactions", produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TransactionDto>> transactions(
            @ApiParam(name = "limit", value = "The upper limit of Transactions returned")
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @ApiParam(name = "accountNumber", value = "account number for with transactions are being queried")
            @PathVariable(value = "accountNumber") Long accountNumber) {


        log.info("received GET request for /transactions for account:{} with limit:{} ", accountNumber, limit);

        var transactions = transactionService.getTransactionDtos(accountNumber, limit);
        return ResponseEntity.ok(transactions);
    }
}
