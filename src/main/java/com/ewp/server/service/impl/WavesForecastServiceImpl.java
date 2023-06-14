package com.ewp.server.service.impl;

import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ewp.server.service.WavesForecastService;
import com.ewp.server.persistence.entity.analysis.EWave;
import com.ewp.server.persistence.entity.analysis.Candlestick;
import com.ewp.server.persistence.repository.analysis.EWaveDao;
import com.ewp.server.persistence.repository.analysis.CandlestickDao;
import com.ewp.server.dto.WaveDTO;
import com.ewp.server.dto.WaveTreeDTO;
import com.ewp.server.dto.mapper.EWaveCustomMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import reactor.core.publisher.Mono;


@Service("wavesForecastService")
@PropertySource(value = { "classpath:application.properties" })
public class WavesForecastServiceImpl implements WavesForecastService {

    private EWaveDao eWaveDao;
    @Autowired
    public void setEWaveDao(EWaveDao eWaveDao) {
        this.eWaveDao = eWaveDao;
    }
    private CandlestickDao candlestickDao;
    @Autowired
    public void setCandlestickDao(CandlestickDao candlestickDao) {
        this.candlestickDao = candlestickDao;
    }

    @Override 
    @Transactional
    public WaveTreeDTO select_TopRated() {
        WaveTreeDTO ret = null;
        
        EWave root = null;
        Optional<EWave> query = this.eWaveDao.findById(1L);    
        if(query.isPresent())             
            root = query.get();
        
        // - пока нет никакой логики      

        ret = EWaveCustomMapper.mapEWaveToWaveTreeDTO(root,true);
        
        return ret;
    }
    
    @Override 
    public EWave select_WaveInfo(String wave_id) {
        EWave ret = null;
        
        Optional<EWave> query = this.eWaveDao.findById(Long.parseLong(wave_id));    
        if(query.isPresent())             
            return query.get();
        else       
           return null;
    }

    @Override 
    @Transactional 
    public String save_Wave(WaveDTO waveDTO) {
        String ret = "";
        
        EWave wave = EWaveCustomMapper.mapWaveDTOToEWave(waveDTO, this.eWaveDao, this.candlestickDao);
        if(null==wave)  
            ret += "Problems with saving Wave object";
        else 
        {   
            if(wave.getId()!=0) 
                this.eWaveDao.mergeAndFlush(wave);
            else     
                wave = this.eWaveDao.persistAndFlush(wave);
            
            ret = Long.toString(wave.getId());    
        }  

        return ret;
    }

    @Override 
    @Transactional 
    public String delete_Wave(WaveDTO waveDTO) {
        String ret = "";
        
        EWave wave = EWaveCustomMapper.mapWaveDTOToEWave(waveDTO, this.eWaveDao, this.candlestickDao);
        if(null==wave)  
            ret += "Problems with deleting Wave object";
        else 
            this.eWaveDao.delete(wave);

        return ret;
    }

}