package pj.tpog11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pj.tpog11.repositories.OrderRepository;
import pj.tpog11.repositories.ProductRepository;
import pj.tpog11.repositories.UserRepository;
import pj.tpog11.model.Order1;
import pj.tpog11.model.User1;

import java.util.ArrayList;

@Controller
public class OrderController {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

//    @GetMapping("/cart")
//    public String getCart(Model model){
//        // Assuming that the user has at least one order
//        User1 currentUser = userRepository.findByName("John Doe"); // Placeholder logic
//        Order1 currentOrder = currentUser.getOrders().get(0);
//        model.addAttribute("order", currentOrder);
//        return "cart";
//    }
//
//    @PostMapping("/order")
//    public String placeOrder(Model model){
//        // Assuming that the user has at least one order
//        User1 currentUser = userRepository.findByName("John Doe"); // Placeholder logic
//        Order1 currentOrder = currentUser.getOrders().get(0);
//
//        // Your logic to handle payment and order placement goes here
//
//        orderRepository.save(currentOrder);
//        currentUser.setOrders(new ArrayList<>()); // Clear the cart after placing the order
//
//        model.addAttribute("order", currentOrder); // Add the order to the model for the confirmation page
//        return "confirmation";
//    }


    @RequestMapping("/orders")
    public String getOrders(Model model){
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }
}
