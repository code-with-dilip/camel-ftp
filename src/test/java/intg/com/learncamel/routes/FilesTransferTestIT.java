package com.learncamel.routes;

import com.learncamel.CamelFtpApplication;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest( classes = {CamelFtpApplication.class},properties = { "camel.springboot.java-routes-include-pattern=com/learncamel/routes/FilesTransfer*"})
public class FilesTransferTestIT {

    @Autowired
    ProducerTemplate template;

    @Autowired
    CamelContext context;

    @Before
    public void startCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("target/inbox"));
        FileUtils.deleteDirectory(new File("target/outbox"));
    }

    @Test
    public void fileMove_Success() throws InterruptedException {

        NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();

        template.sendBodyAndHeader("file://target/inbox", "Hello World Integration test",
                Exchange.FILE_NAME, "hello.txt");

        Thread.sleep(2000);

        File target = new File("target/outbox/hello.txt");
        assertTrue("File not moved", target.exists());
        String content = context.getTypeConverter()
                .convertTo(String.class, target);
        System.out.println("content : " + content);
        assertEquals("Hello World Integration test", content);


    }


}
