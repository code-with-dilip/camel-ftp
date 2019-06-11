package com.learncamel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MockRouteTest extends CamelTestSupport {

    @Before
    public void startCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("target/mock"));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new MockRoute();
    }


    @Test
    public void testQuote() throws Exception {
        MockEndpoint quote = getMockEndpoint("mock:quote");
        quote.expectedMessageCount(1);

        template.sendBodyAndHeader("file://target/mock", "Hello World",
                Exchange.FILE_NAME, "hello.txt");

        Thread.sleep(5000);

        quote.assertIsSatisfied();

    }

    @Test
    public void testQuote_UsingStub() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:quote");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived("Hello World","Hello World1");


        template.sendBodyAndHeader("stub:file://target/mock", "Hello World",
                Exchange.FILE_NAME, "hello.txt");

        template.sendBodyAndHeader("stub:file://target/mock", "Hello World1",
                Exchange.FILE_NAME, "hello.txt");

        mockEndpoint.assertIsSatisfied();

    }

}
