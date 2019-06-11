package com.learncamel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MockRouteTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new MockRoute();
    }

    @Before
    public void startCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("target/mock"));
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
    public void testQuote_UsingStub_expectBody() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:quote");
        mockEndpoint.expectedBodiesReceived("Hello World", "Hello World1");

        template.sendBodyAndHeader("stub:file://target/mock", "Hello World",
                Exchange.FILE_NAME, "hello.txt");

        template.sendBodyAndHeader("stub:file://target/mock", "Hello World1",
                Exchange.FILE_NAME, "hello.txt");

        mockEndpoint.assertIsSatisfied();

    }

    @Test
    public void testQuote_UsingStub_recievedExchange() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:quote");
        mockEndpoint.expectedMessageCount(2); // mandatory, otherwise test will fail

        template.sendBodyAndHeader("stub:file://target/mock", "Hello World",
                Exchange.FILE_NAME, "hello.txt");

        template.sendBodyAndHeader("stub:file://target/mock", "Hello World1",
                Exchange.FILE_NAME, "hello.txt");

        assertMockEndpointsSatisfied(); // mandatory, otherwise test will fail

        List<Exchange> exchangeList = mockEndpoint.getReceivedExchanges();
        String body1 = exchangeList.get(0).getIn().getBody(String.class);
        assertEquals("Hello World", body1);
        String body2 = exchangeList.get(1).getIn().getBody(String.class);
        assertEquals("Hello World1", body2);


    }

}
