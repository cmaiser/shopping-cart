package com.auchris.shoppingcart.repository;

import com.auchris.shoppingcart.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByOrderByNameAsc();

    @Query("SELECT i from Item i JOIN i.carts c ON c.quantity > 0")
    List<Item> findAllByCartOrderByNameAsc();

}
