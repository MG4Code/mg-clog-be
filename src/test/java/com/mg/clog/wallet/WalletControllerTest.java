package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import com.mg.clog.wallet.data.repo.WalletRepository;
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
public class WalletControllerTest {

  @LocalServerPort
  int randomServerPort;

  @MockBean
  private WalletRepository repo;

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
  public void get_wallet() {
    when(repo.findAll()).thenReturn(Flux.just(new Wallet().setName("foobar")));

    webTestClient.get()
      .uri("/clog/v1/wallet")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectBodyList(Wallet.class).hasSize(1)
    ;
  }

  // TODO add test with userId
//  @Test
//  @WithMockUser(username = "georg", roles = "ADMIN")
//  @WithUserDetails(userDetailsServiceBeanName="myUserDetailsService")
//  public void get_my_wallet() {
//    when(repo.findAll()).thenReturn(Flux.just(new Wallet().setName("foobar")));
//
//    webTestClient.get()
//      .uri("/clog/v1/wallet/my")
//      .accept(MediaType.APPLICATION_JSON)
//      .exchange()
//      .expectStatus().isOk()
//      .expectBodyList(Wallet.class).hasSize(1)
//    ;
//  }

}
