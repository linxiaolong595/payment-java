package com.jhzf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class GatewayBack
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayBack.class, args);
    }
}
