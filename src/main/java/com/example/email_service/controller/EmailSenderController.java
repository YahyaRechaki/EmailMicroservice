package com.example.email_service.controller;

import com.example.email_service.EmailService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@CrossOrigin
@RestController
@RequestMapping("/email")
public class EmailSenderController {

    private EmailService emailService;

    public EmailSenderController(EmailService emailService) {
        this.emailService = emailService;
    }

//    @RequestMapping(method= RequestMethod.GET, path="/get-verification-code", produces = MediaType.TEXT_PLAIN_VALUE)
//    public ResponseEntity<Integer> getCode(@RequestBody String email){
//        try {
//            int code = emailService.getVerificationCode(email);
//            return new ResponseEntity<>(code, HttpStatus.OK);
//        } catch (Exception ex) {
//            System.out.println("ex : " + ex );
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @RequestMapping(method= RequestMethod.POST, path="/get-verification-code")
    public int getCode(@RequestBody String email){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(email, headers);
        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:9090/auth/get-verification-code", HttpMethod.POST, request, Integer.class);

        return response.getBody();

    }



    @RequestMapping(method = RequestMethod.POST, path="/send-verification-code")
    public ResponseEntity<Integer> sendCode(@RequestBody String email) throws Exception {
        int code = emailService.getVerificationCode(email);
        if(code == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(code, HttpStatus.OK);
    }
}
