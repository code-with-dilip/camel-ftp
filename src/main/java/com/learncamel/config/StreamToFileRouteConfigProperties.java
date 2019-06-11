package com.learncamel.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("stream-file-route")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamToFileRouteConfigProperties {
    private String fromRoute;
    private String toRoute;

}
