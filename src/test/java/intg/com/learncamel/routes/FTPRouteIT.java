package com.learncamel.routes;

import com.learncamel.CamelFtpApplication;
import com.learncamel.TestApplication;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.learncamel.constants.FTPConstants.FTP_INPUT_DIRECTORY;
import static com.learncamel.constants.FTPConstants.FTP_OUTPUT_DIRECTORY;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {CamelFtpApplication.class},properties = {"camel.springboot.java-routes-include-pattern=com/learncamel/routes/FTPRoute*"})
public class FTPRouteIT {

    @Autowired
    ProducerTemplate template;

    @Autowired
    CamelContext context;

    @Before
    public void startCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File(FTP_INPUT_DIRECTORY));
        FileUtils.deleteDirectory(new File(FTP_OUTPUT_DIRECTORY));
    }

    @Test
    public void ftpFileTransferTest() throws InterruptedException {

        NotifyBuilder notify = new NotifyBuilder(context)
                .wereSentTo("file://" + FTP_OUTPUT_DIRECTORY).whenCompleted(1)
                .create();


        String fileName = "hello.txt";
        String fileContent = "Hello World Integration test";

        template.sendBodyAndHeader("file://" + FTP_INPUT_DIRECTORY, "Hello World Integration test",
                Exchange.FILE_NAME, fileName);

        assertTrue(notify.matches(10, TimeUnit.SECONDS));

        File target = new File(FTP_OUTPUT_DIRECTORY + "/" + fileName);
        assertTrue("File not moved", target.exists());
        String content = context.getTypeConverter()
                .convertTo(String.class, target);
        System.out.println("content : " + content);
        assertEquals(fileContent, content);

    }

}
