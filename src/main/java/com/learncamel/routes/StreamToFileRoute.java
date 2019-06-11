package com.learncamel.routes;

import com.learncamel.config.StreamToFileRouteConfigProperties;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@NoArgsConstructor
public class StreamToFileRoute extends RouteBuilder {

   @Autowired
   private StreamToFileRouteConfigProperties streamToFileRouteConfigProperties;

    public StreamToFileRoute(StreamToFileRouteConfigProperties _streamToFileRouteConfigProperties) {
        this.streamToFileRouteConfigProperties = _streamToFileRouteConfigProperties;
    }

    @Override
    public void configure() throws Exception {

        log.info("To route Value  {} ", streamToFileRouteConfigProperties.getToRoute());
        from(streamToFileRouteConfigProperties.getFromRoute())
                .setHeader("CamelFileName", () -> LocalDateTime.now().toString())
                .log("File Name is : ${file:name}")
                .to(streamToFileRouteConfigProperties.getToRoute());

    }
}
