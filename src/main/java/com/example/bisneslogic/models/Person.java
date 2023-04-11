package com.example.bisneslogic.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "person")
public class Person {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotEmpty
    @Size(min = 2, max = 30, message = "Name should be greater then 2 and lower then 30")
    private String name;
//    private String pas
//    sword;
    @Column(name = "email")
    @Email
    @NotEmpty
    private String email;

    @Column(name = "age")
    @Min(value = 0, message = "ege shuld be grater then 0")
    private Integer age;


    @Column(name = "password")
    private String password;

    public Person() {
    }

    public Person(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //    @JsonIgnore
//    @OneToMany(mappedBy = "user",
//            fetch = FetchType.LAZY)
//    private List<Order> orders;
//    private Boolean archive;

//    @Enumerated(EnumType.STRING)
//
//    private Role role;

//    @OneToOne(cascade = CascadeType.REMOVE)
//    private Bucket bucket;




}