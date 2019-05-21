package com.auchris.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
User represents a principal, which can be authenticated.  The application currently supports one User,
but the data model is set up to support multiple users for future releases

User has a many-to-many relationship with Item
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @JsonIgnore
    private String password;

    @NonNull
    private String role;

}
