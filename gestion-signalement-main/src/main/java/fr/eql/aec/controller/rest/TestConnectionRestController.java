package fr.eql.aec.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConnectionRestController {
    
    private static final Logger logger = LoggerFactory.getLogger(TestConnectionRestController.class);
    
    @GetMapping(value = "/test")
    public ResponseEntity<Boolean> pong() 
    {
        logger.info("Démarrage des services OK .....");
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}