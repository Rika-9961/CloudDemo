package com.hitwh.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        // 可以在这里配置ModelMapper的属性
        // 例如：modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return new ModelMapper();
    }
}
