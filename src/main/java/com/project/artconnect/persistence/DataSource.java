package com.project.artconnect.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

public class DataSource {
    public static HikariDataSource makeDataSource() {
        HikariConfig cfg = new HikariConfig();
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        int maxPoolSize = Integer.parseInt(dotenv.get("DB_MAX_POOL_SIZE"));
        int minPoolSize = Integer.parseInt(dotenv.get("DB_MIN_POOL_SIZE"));
        int connectionTimeout = Integer.parseInt(dotenv.get("DB_CONNECTION_TIMEOUT"));
        int idleTimeout = Integer.parseInt(dotenv.get("DB_IDLE_TIMEOUT"));
        int timeToLive = Integer.parseInt(dotenv.get("DB_TTL"));
        cfg.setJdbcUrl(url);
        cfg.setUsername(username);
        cfg.setPassword(password);
        cfg.setMinimumIdle(minPoolSize);
        cfg.setMaximumPoolSize(maxPoolSize);
        cfg.setConnectionTimeout(connectionTimeout);
        cfg.setIdleTimeout(idleTimeout);
        cfg.setMaxLifetime(timeToLive);
        return new HikariDataSource(cfg);
    }
}