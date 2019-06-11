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
        template.sendBody("stub:stream:in?promptMessage=Enter something:", "ABC" );
        assertMockEndpointsSatisfied();

    }

    @Test
    public void testEndpoints1() throws InterruptedException {

        String expected="DEF";
        MockEndpoint mock = getMockEndpoint("mock:output");
        mock.expectedBodiesReceived(expected);
        template.sendBody("stub:stream:in?promptMessage=Enter something:", "DEF" );
        assertMockEndpointsSatisfied();

    }

    public StreamToFileRouteConfigProperties buildMockStreamToFileConfig(){

        return StreamToFileRouteConfigProperties.builder()
                .fromRoute("stub:stream:in?promptMessage=Enter something:")
                .toRoute("mock:output")
                .build();
    }

}
