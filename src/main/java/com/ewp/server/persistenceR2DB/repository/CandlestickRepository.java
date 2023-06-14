package com.ewp.server.persistenceR2DB.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.ewp.server.persistenceR2DB.entity.Candlestick;

import java.time.LocalDateTime;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface CandlestickRepository extends ReactiveCrudRepository<Candlestick, Long> {
    
    @Query("SELECT cs.* FROM candlestick cs inner join series sr on cs.series_id = sr.id  where ticktime = (select MAX(cs_.ticktime) FROM candlestick cs_ inner join series sr_ on cs_.series_id = sr_.id where sr_.series = :series) and sr.series = :series")
    Mono<Candlestick> findLastBy_Series(String series);

    @Query("SELECT cs.* FROM candlestick cs " + 
    " inner join series sr on cs.series_id = sr.id " +
	" inner join scale sc on cs.scale_id = sc.id " +
	" where " +
	" sr.series = :series and sc.scale = :scale " +
	" and " +
	" cs.ticktime = " +
	"( " +
	"	select MAX(cs_.ticktime) FROM candlestick cs_  " +
	"	inner join series sr_ on cs_.series_id = sr_.id " +
    "   inner join scale sc_ on cs_.scale_id = sc_.id " +
	"   where sr_.series = :series and sc_.scale = :scale " +
	")")
    Mono<Candlestick> findLastBy_SeriesScale(String series, String scale);

    @Query("SELECT * FROM candlestick where scale_id = :scale_id and series_id = :series_id order by ticktime")
    Flux<Candlestick> findBy_SeriesScale(@Param("series_id") int series_id, @Param("scale_id") int scale_id);


    /*@Query("SELECT * FROM candlestick  where ticktime = (select MAX(ticktime) FROM candlestick where scale = :scale) and scale = :scale")
    Mono<Candlestick> findLastByScale(String scale);

    @Query("SELECT * FROM candlestick   where scale = :scale  and ticktime >= :dateFrom order by ticktime")
    Flux<Candlestick> findByScaleDateAfter(@Param("scale") String scale, @Param("dateFrom") LocalDateTime dateFrom);

    @Query("SELECT * FROM candlestick  where scale = :scale  and ticktime >= :dateFrom and ticktime < :dateTo order by ticktime")
    Flux<Candlestick> findByScaleBetweenDates(@Param("scale") String scale, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
    */
}