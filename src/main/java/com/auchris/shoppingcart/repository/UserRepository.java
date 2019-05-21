package com.auchris.shoppingcart.repository;

import com.auchris.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String username);
}
