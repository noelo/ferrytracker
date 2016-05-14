package com.noctest;

import com.noctest.schedules.LoginTask;
import com.noctest.schedules.GetBusLocationTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TransportDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportDataApplication.class, args);
    }
}
