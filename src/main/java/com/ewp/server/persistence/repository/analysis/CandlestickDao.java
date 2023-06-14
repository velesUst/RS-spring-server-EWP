package com.ewp.server.persistence.repository.analysis;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ewp.server.persistence.entity.analysis.Candlestick;

/**
 * Created by ...
 */
@Repository("candlestickDao")
public interface CandlestickDao extends JpaRepository<Candlestick, Long>  {
    
    @Query("FROM Candlestick cs where cs.id = 1")
    Candlestick findLastBy_SeriesScale();

}
