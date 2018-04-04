package com.globallogic.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class ElasticMessageQueueConfig {

    @Value("${queue.accessKey}")
    private String accessKey;

    @Value("${queue.secretKey}")
    private String secretKey;

    @Value("${queue.endpoint}")
    private String endpoint;

    @Value("${queue.queueName}")
    private String queueName;

    @Bean
    public AmazonSQSClient amazonSQSClient() {
        AmazonSQSClient client = new AmazonSQSClient(new BasicAWSCredentials(accessKey, secretKey));
        client.setEndpoint(endpoint);
        client.createQueue(queueName);
        return client;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
