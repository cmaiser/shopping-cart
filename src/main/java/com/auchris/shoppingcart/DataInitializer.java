package com.auchris.shoppingcart;

import com.auchris.shoppingcart.model.Item;
import com.auchris.shoppingcart.model.User;
import com.auchris.shoppingcart.repository.ItemRepository;
import com.auchris.shoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Initialize some starting data and insert it into the H2 database:

1 user with username: chris, password: chris (encoded)
2 several store items

This initializer does not add carts or any relational data between User
and Item
 */

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ItemRepository itemRepository;

    private PasswordEncoder encoder = encoder();

    private List<User> users = new ArrayList<User>(
            Arrays.asList(
                    new User("chris", encoder.encode("chris"), "USER")
                    //Support for multiple users in future release
                )
    );

    private List<Item> items = new ArrayList<Item>(
            Arrays.asList(
                    new Item("Coffee", "Programmer Fuel", 15d),
                    new Item("Router", "Smooth out the edges of boards", 100d),
                    new Item("Waffle Iron", "Makes waffles that look like Death Stars", 50d),
                    new Item("The Iron Throne", "Allows you to rule the seven kingdoms", 700d),
                    new Item("500 Lizards", "Way more than 200 lizards", 500d),
                    new Item("Cannon Ball", "Guaranteed to fire", 12.50d),
                    new Item("Infinity Guantlet", "Buy it before Thanos does", 999d)
            )
    );

    @Override
    public void run(String... args) throws Exception {
        userRepository.saveAll(users);
        itemRepository.saveAll(items);



        log.info("Users Initialized:");
        for(User user : userRepository.findAll()) {
            log.info(user.toString());
        }

        log.info("Items Initialized:");
        for(Item item : itemRepository.findAll()) {
            log.info(item.toString());
        }
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
