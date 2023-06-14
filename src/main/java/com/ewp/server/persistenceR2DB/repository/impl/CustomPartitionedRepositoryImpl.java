package com.ewp.server.persistenceR2DB.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import com.ewp.server.persistenceR2DB.entity.Partitioned;
import com.ewp.server.persistenceR2DB.entity.Scale;
import com.ewp.server.persistenceR2DB.entity.Candlestick;
import org.springframework.stereotype.Repository;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import com.ewp.server.persistenceR2DB.repository.CustomPartitionedRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

@Repository
public class CustomPartitionedRepositoryImpl implements CustomPartitionedRepository {

    private final DatabaseClient databaseClient;

    @Autowired
    public CustomPartitionedRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }
    
    BiFunction<Row, RowMetadata, Partitioned> MAPPING_FUNCTION = (row, rowMetaData) -> Partitioned.builder()
            .id(row.get("prid", Long.class))
//            .scale(row.get("scale", String.class))
            .candlestick_id(row.get("candlestick_id", Long.class))
            .candlestick(Candlestick.builder()
                        .id(row.get("csid", Long.class))
//                      .scale(row.get("scale", String.class))
                        .ticktime(row.get("ticktime", LocalDateTime.class))
                        .build())
            .build();

    public Flux<Partitioned> findAll ( ) {
        String query =
                "SELECT " +
                "pr.id as prid, pr.scale, pr.candlestick_eurusd_id, cs.id as csid, cs.scale, cs.ticktime, cs.volume, cs.open, cs.high, cs.close " +
                "FROM partitioned_eurusd pr " +
                "INNER JOIN candlestick_eurusd cs " +
                "ON cs.id = pr.candlestick_eurusd_id";
         
        return databaseClient.sql(query)
                .map(MAPPING_FUNCTION::apply)
                .all();
    }

    public Mono<Partitioned> findLastByScale(String scale) {
        String query =
                "SELECT " +
                "pr.id as prid, pr.scale, pr.candlestick_eurusd_id, cs.id as csid, cs.scale, cs.ticktime, cs.volume, cs.open, cs.high, cs.close " +
                "FROM partitioned_eurusd pr " +
                "INNER JOIN candlestick_eurusd cs " +
                "ON cs.id = pr.candlestick_eurusd_id " +
                "where cs.ticktime = (select MIN(cs_.ticktime) FROM candlestick_EURUSD cs_ INNER JOIN partitioned_eurusd pr_ ON pr_.candlestick_eurusd_id = cs_.id  where pr_.scale = :scale ) and  pr.scale = :scale ";
         
        return databaseClient.sql(query)
                .bind("scale", scale)
                .map(MAPPING_FUNCTION::apply)
                .one();
    }

}