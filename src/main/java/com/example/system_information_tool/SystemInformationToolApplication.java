package com.example.system_information_tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@SpringBootApplication
public class SystemInformationToolApplication {

    public static void main(String[] args) {
        // Start the Spring Boot application
        SpringApplication.run(SystemInformationToolApplication.class, args);
    }

    // PostConstruct ensures this runs after the Spring Boot application starts
    @PostConstruct
    public void init() {
        redirectSystemOutAndErr();
    }

    // Method to redirect stdout and stderr to separate files
    private void redirectSystemOutAndErr() {
        try {
            // Redirect stdout to stdout.log
            PrintStream out = new PrintStream(new FileOutputStream("stdout.log", true)); // 'true' to append
            System.setOut(out);

            // Redirect stderr to stderr.log
            PrintStream err = new PrintStream(new FileOutputStream("stderr.log", true)); // 'true' to append
            System.setErr(err);

            // Test log messages
            System.out.println("System.out is now redirected to stdout.log");
            System.err.println("System.err is now redirected to stderr.log");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
