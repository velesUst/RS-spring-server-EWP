package com.ewp.server.controller;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

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
import com.ewp.server.persistenceR2DB.entity.Scale;
import com.ewp.server.persistenceR2DB.entity.Partitioned;
import com.ewp.server.persistenceR2DB.repository.CandlestickRepository;
import com.ewp.server.persistenceR2DB.repository.PartitionedRepository;
import com.ewp.server.persistenceR2DB.repository.CustomPartitionedRepository;
import com.ewp.server.dto.DataRequest;
import com.ewp.server.dto.ChartPointDTO;

//@Slf4j
@Controller
public class DataQuery {

    private static final Logger LOG = LogManager.getLogger(DataQuery.class);

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

   
    @MessageMapping("fetch.data")
    public Flux<ChartPointDTO> dataAll(DataRequest req) throws Exception {
        int scale_id = 0, 
            series_id;

        for(Scale s : Scale.values())
        if( s.getValue().equals(req.getScale()))
            scale_id = s.getId();

        switch (req.getSeries()) {    // - брать из бд
            case "EURUSD": series_id = 1;
                break; 
            default: series_id = 0;
                break;
        }
               
        return candlestickRepository.findBy_SeriesScale(series_id, scale_id)
                .map( cs -> {
                    ChartPointDTO cp = ChartPointDTO.builder()
                        .id(cs.getId())
                        .x(cs.getTicktime())
                        .y(cs.getHigh())
                        .build();
                    return cp;                        
                });
    } 

    @MessageMapping("fetch.currentVal")
    public Mono<Candlestick> currentVal_rs(String series) {
        return candlestickRepository.findLastBy_Series(series);
    }  

}
