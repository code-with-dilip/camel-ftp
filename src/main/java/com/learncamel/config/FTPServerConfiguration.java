package com.learncamel.config;

import com.learncamel.annotation.ExcludeFromTests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ExcludeFromTests
public class FTPServerConfiguration {

    @Bean(initMethod = "initFtpServer",destroyMethod = "shutdownServer")
    public FTPServer ftpServer(){
        return new FTPServer();
    }
}
