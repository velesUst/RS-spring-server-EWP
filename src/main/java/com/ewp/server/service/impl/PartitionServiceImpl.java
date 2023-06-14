package com.ewp.server.service.impl;

import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.entity.Partitioned;
import com.ewp.server.persistenceR2DB.repository.CandlestickRepository;
import com.ewp.server.persistenceR2DB.repository.PartitionedRepository;
import com.ewp.server.persistenceR2DB.repository.CustomPartitionedRepository;
import com.ewp.server.service.PartitionService;
import com.ewp.server.utils.partition.PartitionAlgorithm;
import com.ewp.server.utils.partition.Point;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service("partitionService")
@PropertySource(value = { "classpath:application.properties" })
public class PartitionServiceImpl implements PartitionService {


    private static final Logger LOG = LogManager.getLogger(PartitionServiceImpl.class);

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


    @Override
    public void evaluate_PartitionValues( int ammount )  throws Exception {
        
        /*try {        
            LocalDateTime from_ticktime = null,
                to_ticktime = LocalDateTime.now();
            Candlestick cs_eu = candlestickRepository.findLastByScale("IntraDay").block();
            Partitioned pt_eu = customPartitionedRepository.findLastByScale(Partitioned.Scale.MINUTES.getValue()).block();            
            if(null!=pt_eu) 
                from_ticktime = pt_eu.getCandlestick().getTicktime();
            else     
                from_ticktime = cs_eu.getTicktime();

            LocalDateTime prev_ticktime = null;
            for (LocalDateTime ticktime = from_ticktime; ticktime.isBefore(to_ticktime); ticktime = ticktime.plusHours((long)1)) {
                if(null!=prev_ticktime) {
                    AtomicInteger count = new AtomicInteger();                    
                    List<Point> sticks = 
                        candlestickRepository.findByScaleBetweenDates("IntraDay", prev_ticktime, ticktime)
                        .map(cs-> {   
                            Point point = new Point(cs, count.incrementAndGet()-1);                
                            return point;
                        })
                        .collectSortedList((a, b) ->  Long.compare(a.x, b.x))
                        .block();

                    List<Point> selectedPoints = PartitionAlgorithm.vawes_identify(sticks, ammount);                     
                    Flux<Partitioned> partitioned = Flux.fromIterable(selectedPoints)
                    .map(point-> {                          
                        Partitioned pt = Partitioned.builder()
                        .scale(Partitioned.Scale.MINUTES.getValue())
                        .candlestick_id(point.cs.getId())
                        .build();
                        return pt;
                    }); 

                    try {
                        partitionedRepository
                            .saveAll(partitioned)
                            .blockLast(Duration.ofSeconds(10));
                    } catch (Exception e) {
                        LOG.error(e);
                    }                      
                }

                prev_ticktime = ticktime;
            }


        } catch (Exception e) {
            LOG.error(e);
        }*/

    }

}