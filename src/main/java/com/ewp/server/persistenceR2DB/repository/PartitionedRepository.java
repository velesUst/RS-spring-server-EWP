package com.ewp.server.persistenceR2DB.repository;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.ewp.server.persistenceR2DB.entity.Partitioned;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface PartitionedRepository extends ReactiveCrudRepository<Partitioned, Long> {


    /*@Query("SELECT * FROM candlestick_EURUSD where ticktime = (select MAX(ticktime) FROM candlestick_EURUSD where scale = :scale) and  scale = :scale ")
    Mono<CandlestickEURUSD> findLastByScale(String scale);

    @Query("SELECT * FROM candlestick_EURUSD  where scale = :scale  and ticktime > :dateFrom")
    Flux<CandlestickEURUSD> findByScaleDateAfter(@Param("scale") String scale, @Param("dateFrom") LocalDateTime dateFrom);*/  

    // pr.id, pr.scale, cs.id, cs.scale, cs.ticktime, cs.volume, cs.open, cs.high, cs.close 
    /*private Long id;
    @Column("scale")
    private Scale scale;
    @Column("candlestick_EURUSD_id")
    private long candlestick_EURUSD_id;*/

}