package com.auchris.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

/*
Cart is a join table between User and Item.  It holds holds the quantity value for a
User/Item pair
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(CartKey.class)
public class Cart {

    @Id
    @ManyToOne
    @JsonIgnoreProperties("carts")
    private User user;

    @Id
    @ManyToOne
    @JsonIgnoreProperties("carts")
    private Item item;

    private Integer quantity;

}
