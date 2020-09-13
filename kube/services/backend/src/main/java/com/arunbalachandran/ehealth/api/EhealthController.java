package com.arunbalachandran.ehealth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin // enable only if needed
@RestController
public class EhealthController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String home() {
        return "Hello there!";
    }
}
