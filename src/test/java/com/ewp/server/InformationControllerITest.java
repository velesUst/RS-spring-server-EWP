package com.ewp.server;

import java.util.Optional;
import reactor.core.publisher.Mono;

import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.repository.CandlestickRepository;
import com.ewp.server.controller.InformationController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
//@ExtendWith(SpringExtension.class)   ???????????????????????????????????????
//@WebFluxTest(controllers = InformationController.class)
//@Import(CandlestickEURUSDRepository.class)

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class InformationControllerITest extends AbstractTest {
/*
    @Autowired
    WebTestClient webClient;
*/
 //   @MockBean
 //   CandlestickEURUSDRepository candlestickEURUSDRepository;

    /*@Autowired
	private ApplicationContext wac;

  	private MockMvc mvc;

    @BeforeAll
	public void setup() {

	//	this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/test").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Greetings from Spring Boot!")));
	}*/


    @Test
    void testEcho() {
        RSocketRequester requester = createRSocketRequester();
        Mono<String> response = requester.route("echo-test")
            .data("hello")
            .retrieveMono(String.class);
        StepVerifier.create(response)
            .expectNext("ECHO >> hello")
            .expectComplete()
            .verify();
    }

	/*@Test //  нужна авторизация
	public void getCandlestick() throws Exception {

		CandlestickEURUSD cs = CandlestickEURUSD.builder()
                .scale("Daily")
                .open(123.456)
                .build();
        
		Mockito
            .when(сandlestickEURUSDRepository.findLastByScale("Daily"))
            .thenReturn(Mono.just(cs));

		webClient.get().uri("/candlestick/currentVal/{scale}", "Daily")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.open").isEqualTo("123.456")
            .jsonPath("$.scale").isEqualTo("Daily");
         
        Mockito.verify(сandlestickEURUSDRepository, times(1)).findLastByScale("Daily");

 	}*/

	
}