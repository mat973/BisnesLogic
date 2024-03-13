package com.example.bisneslogic.controllers;


import com.example.bisneslogic.dto.AuthRequestDTO;
import com.example.bisneslogic.dto.JwtResponseDTO;
import com.example.bisneslogic.services.JwtService;
import com.example.bisneslogic.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sayHelloAdmin(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }



    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.GenerateToken(authRequestDTO.getUsername());
            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
            jwtResponseDTO.setAccessToken(token);
            return jwtResponseDTO;
        } else {
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
