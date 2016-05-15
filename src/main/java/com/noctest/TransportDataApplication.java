package com.noctest;

import com.noctest.schedules.LoginTask;
import com.noctest.schedules.GetBusLocationTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TransportDataApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TransportDataApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TransportDataApplication.class, args);
    }
}
