package com.example.bisneslogic.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> listCartItem;

    @OneToOne(mappedBy = "cart")
    private Order order;


    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp creationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public Cart(Timestamp creationDate) {
        this.creationDate = creationDate;
    }




//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<CartItem> getListCartItem() {
//        return listCartItem;
//    }
//
//    public void setListCartItem(List<CartItem> listCartItem) {
//        this.listCartItem = listCartItem;
//    }
//
//    public Timestamp getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(Timestamp creationDate) {
//        this.creationDate = creationDate;
//    }
//

}
