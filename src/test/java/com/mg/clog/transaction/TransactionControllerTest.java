package com.mg.clog.transaction;

import com.mg.clog.transaction.data.model.Transaction;
import com.mg.clog.transaction.data.repo.TransactionRepository;
import com.mg.clog.wallet.data.model.Wallet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

  @LocalServerPort
  int randomServerPort;

  @MockBean
  private TransactionRepository repo;

  private WebTestClient webTestClient;

  @Before
  public void setup() {
    webTestClient = WebTestClient
      .bindToServer()
      .baseUrl("http://localhost:" + randomServerPort)
      .build();
  }

  @Test
  @WithMockUser(username = "georg", roles = "ADMIN")
  public void get_transactions() {
    when(repo.findAll()).thenReturn(Flux.just(new Transaction().setAmount(112233L)));

    webTestClient.get()
      .uri("/clog/v1/transaction")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectBodyList(Wallet.class).hasSize(1)
    ;
  }

}
