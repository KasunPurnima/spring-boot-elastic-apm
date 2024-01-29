package com.example.serviceIntegration.Controller;

import com.example.serviceIntegration.ApplicationStarter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/trace")
@Slf4j
public class TraceController {

    @Autowired
    private Environment environment;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${esbUrl}")
    private String esbUrl;

    private static final String HEADER_ID = "request-id";

    //String addressUrl = System.getenv("BASEURL");
    private static final Logger logger = LogManager.getLogger(ApplicationStarter.class);

    @GetMapping("/sampleData/method1")
    public String method1(String elasticTraceID) {

        logger.info("method1--"+elasticTraceID);
        String userId="OCCC";
        MDC.put(HEADER_ID, userId);
        MDC.clear();
        System.out.println("method1--"+elasticTraceID);
        RestTemplate restTemplate = new RestTemplate();
       /*String response = restTemplate.getForObject(baseUrl+"/method2/"+elasticTraceID+"/", String.class);
        return ResponseEntity.ok("response of method 1----"+ response);*/
        String response = method2(elasticTraceID);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonTree = objectMapper.readTree(response);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            String formattedJson = writer.writeValueAsString(jsonTree);
            return formattedJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "Data not found";
        }
    }

   //@GetMapping("/method2/{elasticTraceID}")
    public String method2(String elasticTraceID) {
        logger.info("method2--"+elasticTraceID);
        System.out.println("method2--"+elasticTraceID);
        String userId="OCCC";
        MDC.put(HEADER_ID, userId);
        MDC.clear();
        String esbcallres = callEsb(elasticTraceID);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonTree = objectMapper.readTree(esbcallres);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            String formattedJson = writer.writeValueAsString(jsonTree);
            return formattedJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "Data not found";
        }
    }

    private String callEsb(String elasticTraceID) {
        logger.info("Info log Inside Method callEsb");
        logger.info("callEsb elasticTraceID--"+elasticTraceID);
        String userId="OCCC";
        MDC.put(HEADER_ID, userId);
        MDC.clear();
        System.out.println("callEsb--"+elasticTraceID);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                esbUrl + "/helloWorld", HttpMethod.GET, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            logger.error("Oops! Error calling endpoint");
            return "Error calling  endpoint";
        }
    }

    @GetMapping("/methodInside")
    public ResponseEntity methodInside() {
        logger.info("Incoming request at {} at - methodInside");
        logger.info("Inside One");
        RestTemplate restTemplate = new RestTemplate();
        String userId="OCCC";
        MDC.put(HEADER_ID, userId);
        MDC.clear();
        String response = restTemplate.getForObject(baseUrl+"/methodInside1", String.class);
        return ResponseEntity.ok("response of methodInside1----" + response);
        //return ResponseEntity.ok("response from /method2 ");
    }

    @GetMapping("/methodInside1")
    public ResponseEntity method3() {
        System.out.println("serviceIntegration ");
        logger.info("Incoming request at {} at - serviceIntegration-methodInside1");
        String userId="OCCC";
        MDC.put(HEADER_ID, userId);
        MDC.clear();
        logger.info("Inside Two");
        return ResponseEntity.ok("Hello methodInside1 successfully completed  ");
    }

    @RequestMapping("/testLog")
    public String testLog() {
        String userId="OCCC";
        MDC.put("applicationName", "Application12");
        MDC.put(HEADER_ID, userId);
        MDC.clear();
        System.out.println("userId--"+userId);
        logger.debug("Debugging log");
        logger.info("Info log");
        logger.warn("This is a warning!");
        logger.error("Oops! We have an Error. OK");
        logger.fatal("Fatal error. Please fix me.");
        MDC.clear();
        return "Den Nam Athii Welaa";
    }
}




































//------------------------------------------Removed--------------------------------------//
    /*@GetMapping("/getAccountDetailsByAccountNo/{accountNo}")
    public String getAccountDetails(@PathVariable String accountNo) {
        logger.info("getAccountDetails--");
        String accountDetailsResponse = callAccountDetailsEndpointbyAccountNo(accountNo);
       //return accountDetailsResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonTree = objectMapper.readTree(accountDetailsResponse);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            String formattedJson = writer.writeValueAsString(jsonTree);
            return formattedJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "Data not found";
        }
    }

    private String callAccountDetailsEndpointbyAccountNo(String accountNo) {

        logger.info("callAccountDetailsEndpoint--" + accountNo);
        logger.info("baseUrl--" + baseUrl);
        System.out.println(baseUrl);
        RestTemplate restTemplate = new RestTemplate();
        // String baseUrl = "http://localhost:8990/bank/accounts";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/getAccountDetailsByNumber/")

     

                .path(accountNo);
        logger.info("Full set URL--" + builder.toUriString());
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, null, String.class);
        System.out.println("-------"+response.getStatusCode());
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            logger.error("Oops! Error calling /accountDetails/ endpoint");
            return "Error calling /getAccountDetails/ endpoint";
        }
    }

    private String callAccountDetailsEndpoint(String accountNo) {
        logger.info("Info log Inside Method");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/getAccountDetails/", HttpMethod.GET, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            logger.error("Oops! Error calling /accountDetails/ endpoint");
            return "Error calling /getAccountDetails/ endpoint";
        }
    }*/
//------------------------------------------Removed--------------------------------------//


