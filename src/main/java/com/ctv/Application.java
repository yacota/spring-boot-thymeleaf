package com.ctv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 *
 * @author jllach
 */
@SpringBootApplication
public class Application 
extends      SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
