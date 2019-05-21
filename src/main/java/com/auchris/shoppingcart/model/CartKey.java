package com.auchris.shoppingcart.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/*
Key for composite id in Cart
 */

@EqualsAndHashCode
public class CartKey implements Serializable {

    private User user;
    private Item item;

}
