package com.auchris.shoppingcart.web;

import com.auchris.shoppingcart.model.Cart;
import com.auchris.shoppingcart.model.Item;
import com.auchris.shoppingcart.model.User;
import com.auchris.shoppingcart.repository.CartRepository;
import com.auchris.shoppingcart.repository.ItemRepository;
import com.auchris.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    /*
    Return a list of all items and their associated quantity (if applicable)
     */
    @GetMapping("/allItems")
    Collection<Item> allItems(Authentication authentication) {

        return itemRepository.findAllByOrderByNameAsc();
    }

    /*
    Return a list of all items and their associated quantity (if applicable)
     */
    @GetMapping("/cart")
    Collection<Item> getCart(Authentication authentication) {

        return itemRepository.findAllByCartOrderByNameAsc();
    }

    /*
    Return a single item and associated item by id
     */
    @GetMapping("/item/{id}")
    ResponseEntity<Item> getItemById(@PathVariable Long id, Authentication authentication) {

        Optional<Item> item = itemRepository.findById(id);
        Item returnItem = item.get();

        return ResponseEntity.ok().body(returnItem);
    }

    /*
    Return authenticated user or empty object if not authenticated
     */
    @GetMapping("/user")
    User currentUserName(Authentication authentication) {

        if(authentication != null) {
            return userRepository.findByName(authentication.getName());
        }

        return new User();
    }

    /*
    Add a new item to the list of known items
     */
    @PostMapping("/item")
    ResponseEntity<Item> addFlavor(@Valid @RequestBody Item item) throws URISyntaxException {

        Item result = itemRepository.save(item);
        return ResponseEntity.created(new URI("/api/item/" + result.getId())).body(result);
    }

    /*
    Add a new cart for a User/Item pair, creating a new many-to-many relationship
     */
    @PostMapping("/cart")
    ResponseEntity<Cart> addRating(@Valid @RequestBody Cart cart, Authentication authentication) throws URISyntaxException {

        if(authentication != null) {

            User user = userRepository.findByName(authentication.getName());
            Item item = cart.getItem();

            cart.setUser(user);
            item.getCarts().add(cart);

            Item result = itemRepository.save(item);

            return ResponseEntity.ok().body(cart);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /*
    Update a current cart fora User/Item pair
     */
    @PutMapping("/cart")
    ResponseEntity<Item> updateRating(@Valid @RequestBody Cart cart, Authentication authentication) throws URISyntaxException {

        User user = userRepository.findByName(authentication.getName());
        Item item = cart.getItem();
        Cart newCart = cartRepository.findByUserAndItem(user, item);

        newCart.setQuantity(cart.getQuantity());
        item.getCarts().clear();
        item.getCarts().add(newCart);

        itemRepository.save(item);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
}
