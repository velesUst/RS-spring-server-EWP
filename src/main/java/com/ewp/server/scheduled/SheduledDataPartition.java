package com.ewp.server.scheduled;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import com.ewp.server.service.PartitionService;

@Component
@EnableAsync
@EnableScheduling
public class SheduledDataPartition {
    
    private static final Logger LOG = LogManager.getLogger(SheduledDataPartition.class);

    @Autowired
    @Qualifier("partitionService")
    PartitionService partitionService;    
    
/*
    @Async
    @Scheduled(fixedDelay = 3600000, initialDelay = 10000)
    public void scheduleTaskAsync_partitionMinutes() {
        LOG.info("Start Partition - minutes");

        try {         

            partitionService.evaluate_PartitionValues( 10 );
            
        } catch(Exception er) { 
            LOG.error(er);
        }  
    }
*/
}