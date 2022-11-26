package com.example.weathertest.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;
}
