package com.ewp.server.dto.mapper;

import com.ewp.server.dto.SimpleSource;
import com.ewp.server.dto.SimpleDestination;
//import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper {

    SimpleDestination sourceToDestination(SimpleSource source);

    SimpleSource destinationToSource(SimpleDestination destination);

}
