package com.ewp.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.lang.*;
import reactor.core.publisher.Mono;
import com.ewp.server.persistenceR2DB.entity.Candlestick;
import com.ewp.server.persistenceR2DB.entity.Scale;

public interface ImportService {


    void import_FX( String series, Scale scale ) throws Exception;

}
