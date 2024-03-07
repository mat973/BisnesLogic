package com.example.bisneslogic.controllers;



import com.example.bisneslogic.models.AuthenticationRequest;
import com.example.bisneslogic.models.AuthenticationResponse;
import com.example.bisneslogic.models.RegisterRequest;
import com.example.bisneslogic.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationService service;

    @PostMapping("/reg")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){


        return ResponseEntity.ok(service.register(request));

    }

    @PostMapping("auth")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
