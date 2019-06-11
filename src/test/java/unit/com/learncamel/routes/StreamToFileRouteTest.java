package com.learncamel.routes;

import com.learncamel.config.StreamToFileRouteConfigProperties;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StreamToFileRouteTest extends CamelTestSupport {

    StreamToFileRouteConfigProperties streamToFileRouteConfigProperties =buildMockStreamToFileConfig();



    @Before
    public void startCleanUp() throws IOException {
       // FileUtils.cleanDirectory(new File("data/input"));
        FileUtils.deleteDirectory(new File("data/output"));
        //FileUtils.deleteDirectory(new File("data/error"));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new StreamToFileRoute(streamToFileRouteConfigProperties);
    }


    @Test
    public void testEndpoints() throws InterruptedException {

        String expected="ABC";
        MockEndpoint mock = getMockEndpoint("mock:output");
        mock.expectedBodiesReceived(expected);
        template.sendBody("direct:start", "ABC" );
        assertMockEndpointsSatisfied();

    }

    public StreamToFileRouteConfigProperties buildMockStreamToFileConfig(){

        return StreamToFileRouteConfigProperties.builder()
                .fromRoute("direct:start")
                .toRoute("mock:output")
                .build();
    }

}
