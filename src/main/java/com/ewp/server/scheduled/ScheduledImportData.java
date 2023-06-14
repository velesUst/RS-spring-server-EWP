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

import com.ewp.server.service.ImportService;
import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.entity.Scale;

@Component
@EnableAsync
@EnableScheduling
public class ScheduledImportData {
    
    private static final Logger LOG = LogManager.getLogger(ScheduledImportData.class);

    @Autowired
    @Qualifier("importService")
    ImportService importService;    


    @Async
    @Scheduled(fixedDelay = 86400000, initialDelay = 10000)
    public void scheduleTaskAsync_ImportData() {
        LOG.info("Start Import");

        /*try {                     
            importService.import_FX( "EURUSD", Scale.MINUTES );
        } catch(Exception er) { LOG.error(er); } 
        try {                     
            importService.import_FX( "EURUSD", Scale.FIVEMINUTES );
        } catch(Exception er) { LOG.error(er); } 
        try {                     
            importService.import_FX( "EURUSD", Scale.FIFTEENTHMINUTES );
        } catch(Exception er) { LOG.error(er); } 
        try {                     
            importService.import_FX( "EURUSD", Scale.HALFHOUR );
        } catch(Exception er) { LOG.error(er); } 
        try {                     
            importService.import_FX( "EURUSD", Scale.HOUR );
        } catch(Exception er) { LOG.error(er); } 
        try {                     
            importService.import_FX( "EURUSD", Scale.DAY );
        } catch(Exception er) { LOG.error(er); } 
        try {                     
            importService.import_FX( "EURUSD", Scale.WEEK );
        } catch(Exception er) { LOG.error(er); }*/ 
        
        LOG.info("End Import");
    }

}