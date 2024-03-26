package com.example.bisneslogic.controllers;


import com.example.bisneslogic.dto.AuthRequestDTO;
import com.example.bisneslogic.dto.AuthenticationDto;
import com.example.bisneslogic.dto.JwtResponseDTO;
import com.example.bisneslogic.dto.MoneyDto;
import com.example.bisneslogic.models.UserInfo;
import com.example.bisneslogic.models.UserRole;
import com.example.bisneslogic.repositories.UserRepository;
import com.example.bisneslogic.repositories.UserRoleRepository;
import com.example.bisneslogic.services.CartServices;
import com.example.bisneslogic.services.JwtService;
import com.example.bisneslogic.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;


    @Autowired
    private CartServices cartServices;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;



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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequestDTO authRequestDTO) {
        // Проверяем, не существует ли уже пользователь с таким именем
        if ((userRepository.existsByUsername(authRequestDTO.getUsername()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Пользователь с таким именем уже существует");
        }

        // Создаем нового пользователя
        UserInfo newUser = new UserInfo();
        newUser.setUsername(authRequestDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequestDTO.getPassword())); // Хешируем пароль
        newUser.setRole("USER");
        newUser.setEmail(authRequestDTO.getEmail());
        newUser.setMoney(0.0);
        // Здесь можно добавить дополнительные детали пользователя, если они есть
//        UserRole userRole = userRoleRepository.findByName("USER"); // Предполагается, что такая роль уже существует в базе данных
//        newUser.getRoles().add(userRole);
        // Сохраняем пользователя в базу данных
        userRepository.save(newUser);

        cartServices.createCart(authRequestDTO.getUsername());


        return ResponseEntity.ok("Пользователь успешно зарегистрирован "+ newUser.getRole());
    }


    @GetMapping("/userInfo")
    public ResponseEntity<UserInfo> getUser(){
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
UserInfo userInfo =userRepository.findByUsername(authentication.getName());
        return ResponseEntity.ok(userInfo);
    }


    @PostMapping("/register1")
    public ResponseEntity<String> registerAdmin(@RequestBody AuthRequestDTO authRequestDTO) {
        // Проверяем, не существует ли уже пользователь с таким именем
        if ((userRepository.existsByUsername(authRequestDTO.getUsername()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Пользователь с таким именем уже существует");
        }

        // Создаем нового пользователя
        UserInfo newUser = new UserInfo();
        newUser.setUsername(authRequestDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequestDTO.getPassword())); // Хешируем пароль
        newUser.setRole("ADMIN");
        newUser.setEmail(authRequestDTO.getEmail());
        newUser.setMoney(0.0);
        // Здесь можно добавить дополнительные детали пользователя, если они есть
//        UserRole userRole = userRoleRepository.findByName("ADMIN"); // Предполагается, что такая роль уже существует в базе данных
//        newUser.getRole().add(userRole);
        // Сохраняем пользователя в базу данных
        userRepository.save(newUser);
        cartServices.createCart(authRequestDTO.getUsername());

        return ResponseEntity.ok("Пользователь успешно зарегистрирован"+ newUser.getRole());
    }


//    @PostMapping("/auth")
//    public ResponseEntity<String> auth(@RequestBody AuthenticationDto authenticationDto){
//        userService.authenticate(authenticationDto);
//
//    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        @PostMapping("/addMoney")
        public ResponseEntity<String> addMoney(@RequestBody MoneyDto money) {
            userDetailsService.addMoney(money.getMoney());
            return ResponseEntity.ok("Money added");
        }




}
