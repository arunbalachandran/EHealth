package com.arunbalachandran.ehealth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.DefaultRedisTypeMapper;
import org.springframework.data.redis.core.convert.MappingRedisConverter;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.core.convert.ReferenceResolver;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.arunbalachandran.ehealth.converters.BytesToUUIDConverter;
import com.arunbalachandran.ehealth.converters.StringToUUIDConverter;
import com.arunbalachandran.ehealth.converters.UUIDToBytesConverter;
import com.arunbalachandran.ehealth.converters.UUIDToStringConverter;


@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisProperties.getHost());
        standaloneConfiguration.setPort(redisProperties.getPort());
        standaloneConfiguration.setPassword(redisProperties.getPassword());
        return new LettuceConnectionFactory(standaloneConfiguration);
    }


    @Bean
    // public RedisTemplate<String, Object> redisTemplate() {
    public RedisTemplate<byte[], byte[]> redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();

        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(
            // Arrays.asList(new UUIDToStringConverter(), new StringToUUIDConverter(), new UUIDToBytesConverter(), new BytesToUUIDConverter())
            Arrays.asList(new UUIDToStringConverter(), new StringToUUIDConverter())
        );
    }
}
