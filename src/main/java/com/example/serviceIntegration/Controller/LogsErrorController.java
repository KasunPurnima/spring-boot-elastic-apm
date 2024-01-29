package com.example.serviceIntegration.Controller;

import com.example.serviceIntegration.ApplicationStarter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/log4J")
@Slf4j
public class LogsErrorController {
    private static final Logger logger = LogManager.getLogger(ApplicationStarter.class);

    @GetMapping("/warning")
    public String warning(){
        logger.warn("This is a warning !");
        return "warning";
    }

    @GetMapping("/info")
    public String info(){
        logger.info("Info log");
        return "info";
    }

    @GetMapping("/debug")
    public String debug(){
        logger.debug("Debugging log");
        return "debug";
    }

    @GetMapping("/error")
    public String error(){
        logger.error("Oops! We have an Error. OK");
        return "error";
    }

    @GetMapping("/Fatal")
    public String Fatal(){
        logger.fatal("Fatal error. Please fix me.");
        return "Fatal";
    }
}
