//package com.example.bisneslogic.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/test")
//public class GreetingsController{
//
//    @GetMapping("/unsecured")
//    public String unsecuredData(){
//        return "Unsecured data";
//    }
//
//    @GetMapping("/secured")
//    public String securedData(){
//        return "Secured data";
//    }
//
//    @GetMapping("/admin")
//    public String adminData(){
//        return "Admin";
//    }
//
//    @GetMapping("/info")
//    public String userData(Principal principal){
//        return  principal.getName();
//    }
//
//    @GetMapping("/g")
//    public ResponseEntity<String> sayHello(){
//        return ResponseEntity.ok("Hellow from Matvey");
//    }
//
//    @GetMapping("/b")
//    public ResponseEntity<String> sayGoodBye() {
//
//        return ResponseEntity.ok("Good by and see ypu later");
//    }
//
//
//    @GetMapping("/a")
// //   @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//    public ResponseEntity<String> sayA() {
//
//        return ResponseEntity.ok("Admin");
//    }
//
//    @GetMapping("/c")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
//    public ResponseEntity<String> sayB() {
//
//        return ResponseEntity.ok("User");
//    }
//}
