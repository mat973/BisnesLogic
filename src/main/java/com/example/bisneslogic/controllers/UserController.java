//package com.example.bisneslogic.controllers;
//
//import com.example.bisneslogic.dto.UserDto;
//import com.example.bisneslogic.models.User;
//import com.example.bisneslogic.services.UserService;
//import com.example.bisneslogic.util.UserErrorResponse;
//import com.example.bisneslogic.util.UserNotCreatedException;
//import com.example.bisneslogic.util.UserNotFoundException;
//import jakarta.validation.Valid;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    private final UserService userService;
//
//    private final ModelMapper modelMapper;
//
//
//
//    @Autowired
//    public UserController(UserService userService,
//                          ModelMapper modelMapper) {
//        this.userService = userService;
//        this.modelMapper = modelMapper;
//
//    }
//
//
//
//    @GetMapping()
//    public List<UserDto> getPeople(){
//        return
//                userService.findAll().stream().map(this::converToUserDto)
//                        .collect(Collectors.toList());
//    }
//
//    @GetMapping("/{id}")
//    public UserDto getPerson(@PathVariable("id") Long id){
//        return converToUserDto(userService.findOne(id));
//
//    }
//
//
//    @PostMapping
//    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDto userDto, BindingResult bindingResult){
//        if (bindingResult.hasErrors()){
//            StringBuilder errorMsg = new StringBuilder();
//
//            List<FieldError> errors = bindingResult.getFieldErrors();
//
//            for (FieldError error: errors){
//                errorMsg.append(error.getField())
//                        .append("-").append(error.getDefaultMessage())
//                        .append(";");
//            }
//            throw  new UserNotCreatedException(errorMsg.toString());
//        }
//        userService.save(convertToUser(userDto));
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    private User convertToUser(UserDto userDto) {
//        return modelMapper.map(userDto, User.class);
//    }
//
//
//    @ExceptionHandler
//    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e){
//        UserErrorResponse response = new UserErrorResponse(
//                "Person with this id wasn't found!",
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e){
//        UserErrorResponse response = new UserErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST    );
//    }
//
//    private UserDto converToUserDto(User user){
//        return modelMapper.map(user, UserDto.class);
//    }
//
//}
