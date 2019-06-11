package com.learncamel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FilesTransferRouteTest extends CamelTestSupport {

    @Before
    public void startCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("target/inbox"));
        FileUtils.deleteDirectory(new File("target/outbox"));
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FilesTransferRoute();
    }

    @Test
    public void fileMove_Success() throws InterruptedException {


        template.sendBodyAndHeader("file://target/inbox", "Hello World",
                Exchange.FILE_NAME, "hello1.txt");

        Thread.sleep(5000);

        File target = new File("target/outbox/hello1.txt");
        assertTrue("File not moved", target.exists());
        String content = context.getTypeConverter()
                .convertTo(String.class, target);
        System.out.println("content : " + content);
        assertEquals("Hello World", content);



    }

}
