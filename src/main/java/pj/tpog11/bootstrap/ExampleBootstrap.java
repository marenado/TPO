package pj.tpog11.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pj.tpog11.model.*;
import pj.tpog11.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Component
public
    class ExampleBootstrap
    implements ApplicationListener<ContextRefreshedEvent> {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    public ExampleBootstrap(ProductRepository productRepository, OrderRepository orderRepository,
                            UserRepository userRepository,
                            CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;

        this.cartItemRepository = cartItemRepository;
    }

    private void initData(){

        Product product1 = new Product();

        product1.setName("Pride and Prejudice");
        product1.setPrice(15.99);
        product1.setDescription("One of Jane Austen's most popular novels, Pride and Prejudice.");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("The Master and Margarita");
        product2.setPrice(20.99);
        product2.setDescription("A classic novel by Russian author Mikhail Bulgakov.");
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("To Kill a Mockingbird");
        product3.setPrice(12.99);
        product3.setDescription("A classic novel by Harper Lee, set in the racial turmoil of the American South in the 1930s.");
        productRepository.save(product3);

        Product product4 = new Product();
        product4.setName("1984");
        product4.setPrice(14.99);
        product4.setDescription("A classic dystopian novel by George Orwell.");
        productRepository.save(product4);

        Product product5 = new Product();
        product5.setName("Moby-Dick");
        product5.setPrice(19.99);
        product5.setDescription("A classic novel by Herman Melville about the quest to capture the great white whale.");
        productRepository.save(product5);


        User1 user1 = new User1();
        user1.setName("John Doe");
        userRepository.save(user1);

//        User1 user1 = new User1();
//        user1.setName("John Doe" + System.currentTimeMillis());
//        userRepository.save(user1);

//        CartItem cartItem1 = new CartItem();
//        cartItem1.setProduct(product1);
//        cartItem1.setQuantity(2);
//
//        CartItem cartItem2 = new CartItem();
//        cartItem2.setProduct(product2);
//        cartItem2.setQuantity(1);

//        Order1 order = new Order1();
//        order.setUser(user1);
//        order.setItems(new ArrayList<>());
//        order.setTotalPrice(0);

//        orderRepository.save(order);





    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }
}
