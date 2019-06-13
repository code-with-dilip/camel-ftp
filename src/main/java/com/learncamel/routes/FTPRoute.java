package com.learncamel.routes;

import com.learncamel.constants.FTPConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.learncamel.constants.FTPConstants.FTP_INPUT_DIRECTORY;
import static com.learncamel.constants.FTPConstants.FTP_OUTPUT_DIRECTORY;

@Component
@Slf4j
public class FTPRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        //file://target/inbox
        from("file://"+FTP_INPUT_DIRECTORY)
                .log("Read the Content  ${body}")
                .to("ftp://learncamel:secret@localhost:21000/target/data/outbox");

        from("ftp://learncamel:secret@localhost:21000/target/data/outbox")
                .log("Read Content from the FTP serverç is : ${body}")
        .to("file://"+FTP_OUTPUT_DIRECTORY);

    }
}
