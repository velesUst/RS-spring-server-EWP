package com.ewp.server;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.repository.CandlestickRepository;

@SpringBootTest
public class CandlestickRepositoryTest {

    //Autowired(required=true)
    //DatabaseClient client;

    @Autowired
    CandlestickRepository candlestickRepository;

   /* @Test
    public void testDatabaseClientExisted() {
        assertNotNull(client);
    }*/

    @Test
    public void testPostRepositoryExisted() {
        assertNotNull(candlestickRepository);
    }

    @Test
    public void testInsertAndQueryR2DB() {
        long series_id = 1, scale_id = 1;
        Candlestick cs = Candlestick.builder()
            .scale_id(scale_id)
            .series_id(series_id)
            .ticktime(LocalDateTime.now())
            .open((double)1.001)
            .high((double)1.001)
            .low((double)1.001)
            .close((double)1.001)
            .volume(0.01)
            .build();
        candlestickRepository.save(cs).block();

        Mono<Candlestick> result = candlestickRepository.findById( cs.getId());       

        StepVerifier
        .create(result)
        .assertNext(stick -> {
          assertEquals((double)1.001, stick.getOpen());
          assertNotNull(stick.getId());
        })
        .expectComplete()
        .verify();

        candlestickRepository.delete(cs).block();
        //assertNotNull(customerRep);
    }
}