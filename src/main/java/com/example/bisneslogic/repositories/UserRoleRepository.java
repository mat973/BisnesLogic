package com.example.bisneslogic.repositories;


import com.example.bisneslogic.models.UserInfo;
import com.example.bisneslogic.models.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    public UserRole findByName(String name);
}
