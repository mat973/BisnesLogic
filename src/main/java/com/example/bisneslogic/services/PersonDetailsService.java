package com.example.bisneslogic.services;

import com.example.bisneslogic.models.Person;
import com.example.bisneslogic.repositories.PersonRepository;
import com.example.bisneslogic.security.PersonDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PersonRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

//    public PersonDto getDate(String p){
//        Optional<Person> person = peopleRepository.findByName(s);
//
//        return ;
//
//    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByName(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person.get());
    }
}
//@Service
//public class PersonDetailsService implements UserDetailsService {
//    private final PersonRepository personRepository;
//@Autowired
//    public PersonDetailsService(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////          Optional<Person> person = personRepository.findByName(username);
////
////          if (person.isEmpty()){
////              throw new UsernameNotFoundException("User not found");
////          }
////
////          return new PersonDetails(person.get());
//
//        return new PersonDetails(personRepository.findByName(username).orElseThrow(()->new UsernameNotFoundException("User not found")));
//
//
//    }
//}
