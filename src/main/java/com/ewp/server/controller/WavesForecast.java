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
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.time.LocalDateTime;  
import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;

import com.ewp.server.service.WavesForecastService;
import com.ewp.server.persistence.entity.analysis.EWave;
import com.ewp.server.dto.WaveTreeDTO;
import com.ewp.server.dto.mapper.EWaveCustomMapper;
import com.ewp.server.dto.*;


//@Slf4j
@Controller
public class WavesForecast {

    private static final Logger LOG = LogManager.getLogger(WavesForecast.class);  

    @Autowired
    private WavesForecastService wavesForecastService;

    @MessageMapping("fetch.topRatedTree")
    public Mono<WaveTreeDTO> topRated() {       

        return Mono.just(wavesForecastService.select_TopRated());
    }  

    @MessageMapping("fetch.waveInfo")
    public Mono<WaveDTO> waveInfo(DataRequest req) {       

        EWave selected = wavesForecastService.select_WaveInfo(req.getWave_id());

        return Mono.just(EWaveCustomMapper.mapEWaveToWaveDTO(selected));
    }

    @MessageMapping("save.wave")
    public Mono<String> saveWave(WaveDTO waveDTO) {       
      
        return  Mono.just(wavesForecastService.save_Wave(waveDTO));
    }    

    @MessageMapping("delete.wave")
    public Mono<String> deleteWave(WaveDTO waveDTO) {       
      
        return  Mono.just(wavesForecastService.delete_Wave(waveDTO));
    }      
}
