package com.ewp.server.dto.mapper;

import com.ewp.server.dto.ChartPointDTO;
import com.ewp.server.dto.WaveDTO;
import com.ewp.server.dto.WaveTreeDTO;
import com.ewp.server.dto.Rd3tAttributes;
import com.ewp.server.dto.Rd3t;
import com.ewp.server.persistence.entity.analysis.EWave;
import com.ewp.server.persistence.entity.analysis.Candlestick;
import com.ewp.server.persistence.repository.analysis.EWaveDao;
import com.ewp.server.persistence.repository.analysis.CandlestickDao;
import com.ewp.server.persistence.entity.analysis.PType;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Optional;

public class EWaveCustomMapper {
    
    public static WaveTreeDTO mapEWaveToWaveTreeDTO(EWave wave, boolean isRoot) {            
       
        WaveTreeDTO waveTreeDTO = WaveTreeDTO.builder()
            .name("Foreman")
            .id(""+wave.getId())
            .__rd3t( 
                Rd3t.builder()
                    .collapsed(true)
                    .depth(0)
                    .id(""+wave.getId())
                    .build() )
            .__rd3tAttributes(
                Rd3tAttributes.builder()
                    .isSelected(false)
                    .showElementState(true)
                    .isRootElement(isRoot)
                    .elementType(isRoot ? "GOTElementType.TASK" : "GOTElementType.OBJECTIVE")
                    .displayLabel("Task")
                    .elementNameLabel("This is a child task")
                    .taskVelocity(isRoot ? "AgileRVelocityIndicatorState.BLOCKED" : "AgileRVelocityIndicatorState.FAST")
                    .tasProgressState("AgileRProgressState.IN_PROGRESS")
                    .build()
            )
            .children(wave.getDecompositeEWave().stream()
                .map(w -> EWaveCustomMapper.mapEWaveToWaveTreeDTO(w,false))
                .collect(Collectors.toList()))
            .build();
        return waveTreeDTO;    
    }

    public static WaveDTO mapEWaveToWaveDTO(EWave wave) {            
        Collection<ChartPointDTO> pointsDTO = wave.getEwaveSticks().stream().
            map(cs -> ChartPointDTO.builder()
                .id(cs.getId())
                .x(cs.getTicktime())
                .y(cs.getHigh())
                .build())
            .collect(Collectors.toList());

        WaveDTO waveDTO = WaveDTO.builder()
            .id(wave.getId())
            .type(wave.getPType().toString().toUpperCase())
            .points(pointsDTO)
            .entetyState("Persistent")
            .build();
        return waveDTO;
    }
    
    public static EWave mapWaveDTOToEWave(WaveDTO waveDTO, EWaveDao eWaveDao, CandlestickDao candlestickDao) {     
        EWave wave = null;
        if(waveDTO.getEntetyState().equals("Persistent") || waveDTO.getEntetyState().equals("Detached")) {
            Optional<EWave> query = eWaveDao.findById(waveDTO.getId());  // - if just set id and call merge afrer set new attributes values ???
            if(query.isPresent()) 
            wave = query.get();
        }
        else 
            wave = new EWave();
        
        //wave.setPTypeValue(PType.of(waveDTO.getType()).getPType());  // - remove after @PrePersist  will start working
        wave.setPType(PType.of(waveDTO.getType()));
          
        Collection<Candlestick> sticks = waveDTO.getPoints().stream().
            map(point -> candlestickDao.findById(point.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        wave.setEwaveSticks(sticks);  
        
        if(null!=waveDTO.getHeaderWave_id()) {   
            EWave headerWave = null;
            Optional<EWave> query = eWaveDao.findById(waveDTO.getHeaderWave_id());  // - if just set id and call merge afrer set new attributes values ???
            if(query.isPresent()) {
                headerWave = query.get();

                Collection<EWave> headerEWaves = wave.getHeaders(); 
                final long header_id = (long)headerWave.getId();
                if(null==headerEWaves)
                    wave.setHeaders(Arrays.asList(headerWave));
                else 
                if(!headerEWaves.stream().anyMatch(w -> (long)w.getId()==header_id)) {
                    headerEWaves.add(headerWave);
                    wave.setHeaders(headerEWaves);
                }        
            }
            else
                throw new RuntimeException("Error at saving wave, he header wave should be saved berore.");
        }

        return wave;
    }
}
