package com.arunbalachandran.ehealth.api;

import com.arunbalachandran.ehealth.dto.LoginRequest;
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
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // parse body and return response
        return new ResponseEntity<>("Hello there " + loginRequest.getEmailid() + " with Pwd : " + loginRequest.getPwd(), HttpStatus.CREATED);
    }
}
