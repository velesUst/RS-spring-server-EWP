package com.ewp.server.persistenceR2DB.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.ewp.server.persistenceR2DB.entity.Partitioned;

import java.time.LocalDate;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;


public interface CustomPartitionedRepository {    

    Flux<Partitioned> findAll ();    
    
    Mono<Partitioned> findLastByScale (String scale);

}