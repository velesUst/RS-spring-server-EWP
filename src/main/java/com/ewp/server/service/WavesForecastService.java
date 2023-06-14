package com.ewp.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.lang.*;
import reactor.core.publisher.Mono;
import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistence.entity.analysis.EWave;
import com.ewp.server.dto.WaveDTO;
import com.ewp.server.dto.WaveTreeDTO;


public interface WavesForecastService {

    WaveTreeDTO select_TopRated();
    
    EWave select_WaveInfo(String wave_id);

    String save_Wave(WaveDTO waveDTO);

    String delete_Wave(WaveDTO waveDTO);
    
}
