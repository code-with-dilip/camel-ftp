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