package com.ewp.server.service.impl;

import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ewp.server.persistenceR2DB.entity.Scale;
import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.entity.Partitioned;
import com.ewp.server.persistenceR2DB.repository.CandlestickRepository;
import com.ewp.server.persistenceR2DB.repository.PartitionedRepository;
import com.ewp.server.service.ImportService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import reactor.core.publisher.Mono;


@Service("importService")
@PropertySource(value = { "classpath:application.properties" })
public class ImportServiceImpl implements ImportService {

    String apiKey = "test";

    private static final Logger LOG = LogManager.getLogger(ImportServiceImpl.class);

    private CandlestickRepository candlestickRepository;
    private PartitionedRepository partitionedRepository;

    @Autowired
    public void setCandlestickRepository(CandlestickRepository candlestickRepository) {
        this.candlestickRepository = candlestickRepository;
    }
    @Autowired
    public void setPartitionedRepository(PartitionedRepository partitionedRepository) {
        this.partitionedRepository = partitionedRepository;
    }


    @Override
    public void import_FX( String series, Scale scale )  throws Exception {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Candlestick last_stick = candlestickRepository.findLastBy_SeriesScale(series, scale.getValue()).block();  // - чтобы избежать дублирование

        String function_, from_ = null, to_ = null, interval = "", outputsize = "";
        long series_id = 0;
        switch (series) {
            case "EURUSD": { from_ = "EUR"; to_ = "USD"; series_id = (long)1; }
                break;
        }    
        switch (scale.getValue()) {
            case "day": { function_ = "FX_DAILY"; outputsize = "&outputsize=full"; }
                break;
            case "week": function_ = "FX_WEEKLY";
                break;
            default:{ function_ = "FX_INTRADAY"; interval = "&interval="+ scale.getValue(); outputsize = "&outputsize=full"; formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");}
                break;
        }    

        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://testttttt.com/query?function="+ function_ + interval +"&to_symbol="+ to_ +"&from_symbol="+ from_ +"&datatype=csv"+outputsize))
		.header("X-RapidAPI-Key", "test")
		.header("X-RapidAPI-Host", "test")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
        try (BufferedReader reader =
                     new BufferedReader(new StringReader( response.body() ))) {
            
            String headerLine = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {                
                
                String[] values = line.split(",");                
                String timeVal = values[0];
                if(timeVal.length()==10) timeVal += " 00:00:00";
                LocalDateTime time = LocalDateTime.parse(timeVal, formatter);
                boolean load = false;
                if(null==last_stick)     
                    load = true;
                else if( last_stick.getTicktime().isBefore(time) )    
                    load = true;
                
                if(load) {
                    Candlestick cs = Candlestick.builder()
                    .scale_id(Long.valueOf(scale.getId()))
                    .series_id(series_id)
                    .ticktime(time)
                    .open(Double.parseDouble(values[1]))
                    .high(Double.parseDouble(values[2]))
                    .low(Double.parseDouble(values[3]))
                    .close(Double.parseDouble(values[4]))
                    .volume(0.01)
                    .build();
                
                    try {
                        candlestickRepository.save(cs).block();
                    } catch (Exception e) {
                        LOG.error(e);
                    }
                }
            }

        }
        // - по ошибке выходим, чтобы не оставлять пробелы в истории значений
    }
    
}