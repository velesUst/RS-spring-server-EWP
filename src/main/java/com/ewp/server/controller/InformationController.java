package com.ewp.server.controller;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.messaging.handler.annotation.MessageMapping;
/*import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.time.LocalDateTime;  
import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;

import com.ewp.server.persistence.entity.secure.AuthToken;
import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.entity.Partitioned;
import com.ewp.server.persistenceR2DB.repository.CandlestickRepository;
import com.ewp.server.persistenceR2DB.repository.PartitionedRepository;
import com.ewp.server.persistenceR2DB.repository.CustomPartitionedRepository;

import com.ewp.server.service.ImportService;


@RestController
public class InformationController {

    private static final Logger LOG = LogManager.getLogger(InformationController.class);

    private CandlestickRepository candlestickRepository;
    private PartitionedRepository partitionedRepository;
    private CustomPartitionedRepository customPartitionedRepository;

    @Autowired
    public void setCandlestickRepository(CandlestickRepository candlestickRepository) {
        this.candlestickRepository = candlestickRepository;
    }
    @Autowired
    public void setPartitionedRepository(PartitionedRepository partitionedRepository) {
        this.partitionedRepository = partitionedRepository;
    }
    @Autowired
    public void setCustomPartitionedRepository(CustomPartitionedRepository customPartitionedRepository) {
        this.customPartitionedRepository = customPartitionedRepository;
    }

    /*@PostMapping("/customer")
    Mono<Void> create(@RequestBody Publisher<Customerrx> customerFlux) {
        return customerRepository.saveAll(customerFlux).then();
    }*/

    // @PreAuthorize("hasRole('USER')")
    @MessageMapping("customer.v1")
    public Flux<Candlestick> dataAll(/* @AuthenticationPrincipal UserDetails user*/ ) throws Exception {
          
        //return candlestickRepository.findByScaleDateAfter("Daily", LocalDateTime.of(2020, 8, 1, 15, 56));
        return candlestickRepository.findBy_SeriesScale(1,1);
    }

    @MessageMapping("customer.v2")
    public Flux<Partitioned> dataAll_(/* @AuthenticationPrincipal UserDetails user*/ ) throws Exception {
          
        return this.customPartitionedRepository.findAll();
    }


    @GetMapping("/customer/{id}")
    public Mono<Candlestick> findById(@PathVariable Long id) {
        return candlestickRepository.findById(id);
    }

   
    @RequestMapping("/test")
	public String index() {
        return "Greetings from Spring Boot!";
    }

    @MessageMapping("echo-test")
    public Mono<String> echoTest(String input) {
        return Mono.just("ECHO >> " + input);
    }

    @MessageMapping("echo")
    public Mono<AuthToken> echo(String input) {
        AuthToken ret = new AuthToken();
        ret.setAccessToken("------"+input);
        return Mono.just(ret);
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }
    static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
        ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {
 
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println(
                      "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }   
}
