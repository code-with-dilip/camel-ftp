# camel-ftp

## Unit Testing using Stub

-   Stub is a component that's part of the camel-core that takes care of stubbing any camel endpoint.

**File Component**

```youtrack
file://target/mock
``` 

**Stub Equivalent**

```youtrack
stub:file://target/mock
```
 
### Route using stub and mock

```youtrack
    from("stub:file://target/mock")
    .log("Read the file")
    .to("mock:quote");
``` 

**mock**

-   Mock is another way through which we can mock out a destination.


### Test using Mock

**Approach 1**

```youtrack
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

```

**Approach 2**

```youtrack
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
```

## Setting Up In Memory FTP Server

-   Add the below dependencies in the build.gralde file. 
```youtrack
    implementation group: 'org.apache.ftpserver', name: 'ftpserver-core', version: '1.0.0'
    implementation group: 'org.apache.ftpserver', name: 'ftplet-api', version: '1.0.0'
    implementation group: 'org.apache.mina', name: 'mina-core', version: '2.1.3'

```

-   Add the below classes in to the project.

```youtrack
FTPServer
FTPServerConfiguration
```

-   Make sure you the **uses.properties** added to the classpath with the below values.

```
ftpserver.user.admin
ftpserver.user.admin.userpassword=admin
ftpserver.user.admin.homedirectory=./
ftpserver.user.admin.writepermission=true
ftpserver.user.learncamel
ftpserver.user.learncamel.userpassword=secret
ftpserver.user.learncamel.homedirectory=./
ftpserver.user.learncamel.writepermission=true
``` 

-   When you start up the application this sould start the application and start the FTP servers through which we can read and write data in to it.

### Code the ftp Camel Route

- Write to the FTP Server

```
        from("file://"+FTP_INPUT_DIRECTORY)
                .log("Read the Content  ${body}")
                .to("ftp://learncamel:secret@localhost:21000/target/data/outbox");

       
```
-   Read from the FTP server.

```youtrack
 from("ftp://learncamel:secret@localhost:21000/target/data/outbox")
                .log("Read Content from the FTP serverç is : ${body}")
        .to("file://"+FTP_OUTPUT_DIRECTORY);
```

