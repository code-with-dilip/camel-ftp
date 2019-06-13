package com.learncamel.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class FilesTransferRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file://target/inbox")
                .log("Transporting from data/input ${body}")
                .to("file://target/outbox");

    }
}
