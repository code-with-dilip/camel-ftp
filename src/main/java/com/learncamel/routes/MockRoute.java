package com.learncamel.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        from("file://target/mock")
                .log("Read the file")
                .to("mock:quote");

        from("stub:file://target/mock")
                .log("Read the file")
                .to("mock:quote");


    }
}
