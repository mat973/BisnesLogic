package com.example.bisneslogic.repositories;

import com.example.bisneslogic.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


//    @Query("select c  from Cart c where Cart.creationDate = ( select  max(Cart.creationDate) from  Cart) ")
    @Query(value = "select * from Cart where Cart.created = (select max(Cart.created) from Cart) and Cart.user_id = :currentUserId", nativeQuery = true)
    Cart findMyCurrentCart(@Param("currentUserId") long user_id);
}
