package com.ewp.server.controller;

import com.ewp.server.dto.Tweet;
import com.ewp.server.dto.TweetRequest;
import reactor.core.publisher.Flux;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import java.time.Duration;

//@Slf4j
@Controller
public class RSocketController {

    
    //@CrossOrigin(origins = "http://localhost:3000")
    @MessageMapping("tweets.by.authort")
    public Flux<Tweet> channel(TweetRequest request) {

        return Flux.fromArray(
            new Tweet[] { 
                new Tweet("eeee---123", "ertt---123"), 
                new Tweet("eeee---345", "ertt---345"), 
                new Tweet("eeee---678", "ertt---678")
            });
	}

   // @CrossOrigin(origins = "http://localhost:3000")
    @MessageMapping("relation.channel")
    Flux<Tweet> channel(final Flux<String> settings/*, @AuthenticationPrincipal UserDetails user*/) {
        //log.info("Received channel request...");
        //log.info("Channel initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());

        // - -------------------
        // - запуск при вызове
        // - периодическая выдача результатов по мере готовности (по 100 штук)
        // - корректировка параметров рассчёта
        // - остановка рассчёта

        return settings
               // .switchMap(setting -> Flux.interval(Duration.ofMillis(200))
                        .map(setting -> new Tweet("-1----"+setting, "-2----"+setting));
    }
}
