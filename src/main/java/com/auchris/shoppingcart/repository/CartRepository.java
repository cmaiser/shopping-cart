package com.auchris.shoppingcart.repository;

import com.auchris.shoppingcart.model.Item;
import com.auchris.shoppingcart.model.Cart;
import com.auchris.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{

    Cart findByUserAndItem(User user, Item item);

}
