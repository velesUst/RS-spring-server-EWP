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

import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.repository.CandlestickRepository;
import com.ewp.server.controller.InformationController;



@SpringBootTest
public class DataQueryTest extends AbstractTest {

    @Test
    void testEcho2() {
       	/*CandlestickEURUSD cs = CandlestickEURUSD.builder()
            .scale("Daily")
            .open(123.456)
            .build();        
		Mockito
            .when(candlestickEURUSDRepository.findLastByScale("Daily"))
            .thenReturn(Mono.just(cs));
        */

        RSocketRequester requester = createRSocketRequester();
        Mono<Candlestick> response = requester.route("fetch.currentVal")
            .data("EURUSD")
            .retrieveMono(com.ewp.server.persistenceR2DB.entity.Candlestick.class);
        /* StepVerifier.create(response)
            .consumeNextWith(cs -> {
                assertThat( );
            })
            .verifyComplete();*/
        StepVerifier.create(response).expectNextCount(1).verifyComplete();
    }
	
}