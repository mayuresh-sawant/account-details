package au.com.wholesale.account.details;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private String accountsUrl;
    private String transactionUrl;


    @BeforeEach
    public void setup() {
        accountsUrl = String.format("http://localhost:%d/accounts", port);
        transactionUrl = String.format("http://localhost:%d/accounts/{id}/transactions", port);
    }

    @Test
    @DisplayName("Given limit=10 should return 10 Accounts")
    public void shouldReturnTenAccounts() {

        var accounts = restTemplate.getForEntity(accountsUrl + "?limit=10", JsonNode[].class);

        assertThat(accounts.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(accounts.getBody()).hasSize(10);
    }

    @Test
    @DisplayName("Given no Limit should return default 20 Accounts")
    public void shouldReturnTwentyAccounts() {

        var accounts = restTemplate.getForEntity(accountsUrl, JsonNode[].class);

        assertThat(accounts.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(accounts.getBody()).hasSize(20);
    }

    @Test
    @DisplayName("Given no Limit should return default 20 Transactions")
    public void shouldReturnTwentyTransactions() {
        //setup
        var body = restTemplate.getForEntity(accountsUrl + "?limit=1", JsonNode[].class);
        var accountId = body.getBody()[0].get("accountNumber").asText();
        var url = transactionUrl.replace("{id}", accountId);

        //test
        var transactions = restTemplate.getForEntity(url, JsonNode[].class);

        //verify
        assertThat(transactions.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transactions.getBody()).hasSize(20);
    }

    @Test
    @DisplayName("Given limit=10 should return 10 Transactions")
    public void shouldReturnTenTransactions() {
        //setup
        var body = restTemplate.getForEntity(accountsUrl + "?limit=1", JsonNode[].class);
        var accountId = body.getBody()[0].get("accountNumber").asText();
        var url = transactionUrl.replace("{id}", accountId);

        //test
        var transactions = restTemplate.getForEntity(url + "?limit=10", JsonNode[].class);

        //verify
        assertThat(transactions.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transactions.getBody()).hasSize(10);
    }

    @Test
    @DisplayName("Given Invalid Account Id, should error")
    public void shouldReturnError() {

        var url = transactionUrl.replace("{id}", String.valueOf(123));

        var response = restTemplate.getForEntity(url, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get("title").asText()).isEqualTo("Account Not found");
        assertThat(response.getBody().get("status").asText()).isEqualTo("400");
        assertThat(response.getBody().get("detail").asText()).isEqualTo("Account \'123\' not found");

    }
}
