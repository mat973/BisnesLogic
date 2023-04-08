//package com.example.bisneslogic.services;
//
//import com.example.bisneslogic.models.User;
//import com.example.bisneslogic.repositories.UserRepository;
//import com.example.bisneslogic.util.UserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional(readOnly = true)
//public class UserService {
//    private final UserRepository userRepository;
//
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//
//    }
//
//    public List<User> findAll(){return userRepository.findAll();}
//
//    public User findOne(Long id){
//        Optional<User> foundUser =  userRepository.findById(id);
//        return foundUser.orElseThrow(UserNotFoundException::new);
//    }
//
//    @Transactional
//    public  void save(User user){ userRepository.save(user);}
//}
