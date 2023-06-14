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
        product1.setName("Apple iPhone 12");
        product1.setPrice(999.99);
        product1.setDescription("Apple iPhone 12 with 64GB Memory");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Samsung Galaxy S21");
        product2.setPrice(899.99);
        product2.setDescription("Samsung Galaxy S21 with 128GB Memory");
        productRepository.save(product2);

        User1 user1 = new User1();
        user1.setName("John Doe");
        userRepository.save(user1);

        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);

        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);

        Order1 order = new Order1();
        order.setUser(user1);
        List<CartItem> items = new ArrayList<>();
        items.add(cartItem1);
        items.add(cartItem2);
        order.setItems(items);
        order.setTotalPrice(product1.getPrice() * cartItem1.getQuantity() + product2.getPrice() * cartItem2.getQuantity());

        cartItem1.setOrder(order); // Set the Order before saving
        cartItem2.setOrder(order); // Set the Order before saving

        cartItemRepository.save(cartItem1); // Save CartItem after setting Order
        cartItemRepository.save(cartItem2); // Save CartItem after setting Order

        orderRepository.save(order); // Save Order after saving CartItems



    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }
}
