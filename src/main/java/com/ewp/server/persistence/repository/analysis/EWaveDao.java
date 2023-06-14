package com.ewp.server.persistence.repository.analysis;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ewp.server.persistence.repository.HibernateRepository;
import com.ewp.server.persistence.entity.analysis.EWave;

/**
 * Created by ...
 */
@Repository("ewaveDao")
public interface EWaveDao extends HibernateRepository<EWave>, JpaRepository<EWave, Long> {
    
    @Query("FROM EWave ewave WHERE ewave.id = :id")
    Optional<EWave> findById(@Param("id") long id);

}
