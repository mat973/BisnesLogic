package com.example.bisneslogic.repositories;


import com.example.bisneslogic.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
    public UserInfo findByUsername(String username);

    public boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE money = (SELECT MAX(money) FROM users) LIMIT 1", nativeQuery = true)
    Optional<UserInfo> findByMaxMoney();
}