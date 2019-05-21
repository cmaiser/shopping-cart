package com.auchris.shoppingcart.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/*
Item represents an item a user can purchase.

Item has a many-to-many relationship with User
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private Double price;

    @OneToMany(mappedBy="item", cascade=CascadeType.MERGE)
    private List<Cart> carts = new ArrayList<>();

}
