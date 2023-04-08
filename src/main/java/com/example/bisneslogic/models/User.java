//package com.example.bisneslogic.models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//
//@Entity
//@Table(name = "person")
//public class User {
//
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "name")
//    @NotEmpty
//    @Size(min = 2, max = 30, message = "Name should be greater then 2 and lower then 30")
//    private String name;
////    private String pas
////    sword;
//    @Column(name = "email")
//    @Email
//    @NotEmpty
//    private String email;
//
//    @Column(name = "age")
//    @Min(value = 0, message = "ege shuld be grater then 0")
//    private Integer age;
//
//
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "user",
//            fetch = FetchType.LAZY)
//    private List<Order> orders;
////    private Boolean archive;
//
////    @Enumerated(EnumType.STRING)
////
////    private Role role;
//
////    @OneToOne(cascade = CascadeType.REMOVE)
////    private Bucket bucket;
//
//
//    public User(Long id, String name, String email, Integer age) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.age = age;
//    }
//
//
//    public User() {
//
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
//}