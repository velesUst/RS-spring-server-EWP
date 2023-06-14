package com.ewp.server;

import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import org.springframework.http.MediaType;


abstract class AbstractTest {

  @Value("${spring.rsocket.server.port}")
  private int serverPort;
  @Autowired
  private RSocketRequester.Builder builder;

  RSocketRequester createRSocketRequester() {
    //return builder.dataMimeType(MediaType.APPLICATION_JSON)
    //    .connect(TcpClientTransport.create(serverPort)).block();
    return builder.dataMimeType(MediaType.APPLICATION_JSON)
        .connect(WebsocketClientTransport.create(serverPort)).block();
  }
}
