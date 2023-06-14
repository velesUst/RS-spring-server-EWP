package com.ewp.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.lang.*;
import reactor.core.publisher.Mono;

public interface PartitionService {

    void evaluate_PartitionValues( int ammount ) throws Exception;

}
