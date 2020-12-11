package com.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select count(*) from users;", nativeQuery = true)
    String getCount();

    User findByEmail(String email);
}