package au.com.wholesale.account.details.controller;

import au.com.wholesale.account.details.dto.AccountDto;
import au.com.wholesale.account.details.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(value = "Account API", tags = {"Accounts"})
public class AccountController {
    private final AccountService accountService;

    @ApiOperation(
            value = "Get Accounts",
            tags = {"Accounts"},
            notes = "Retrieve Accounts as json for customer",
            httpMethod = "GET",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account details as Json"),
            @ApiResponse(code = 500, message = "Failed to retrieve Accounts")
    })
    @GetMapping(value = "/accounts", produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AccountDto>> accounts(
            @ApiParam(name = "limit", value = "The upper limit of accounts returned")
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {

        log.info("received GET request for /account with limit:{} ", limit);

        return ResponseEntity.ok(accountService.getAccountDtos(limit));
    }
}
