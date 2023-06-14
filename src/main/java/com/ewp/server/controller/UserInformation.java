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
import java.util.Map;
import java.util.Date;
import java.time.LocalDateTime;  
import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;

import com.ewp.server.persistence.entity.user.Privilege;
import com.ewp.server.persistence.repository.user.PrivilegeDao;
import com.ewp.server.dto.DataRequest;

@Controller
public class UserInformation {

    private static final Logger LOG = LogManager.getLogger(UserInformation.class);

    private PrivilegeDao privilegeDao;
    @Autowired
    public void setPrivilegeDao(PrivilegeDao privilegeDao) {
        this.privilegeDao = privilegeDao;
    }


    @MessageMapping("fetch.findPrivilege")
    public Mono<Privilege> findPrivilege(String name) {        
        return Mono.just(privilegeDao.findByName(name));
    }  

}
