package pj.tpog11.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pj.tpog11.model.CartItem;
import pj.tpog11.repositories.CartItemRepository;
import pj.tpog11.repositories.OrderRepository;
import pj.tpog11.repositories.ProductRepository;
import pj.tpog11.repositories.UserRepository;
import pj.tpog11.model.Order1;
import pj.tpog11.model.User1;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private final CartItemRepository cartItemRepository;

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private Long currentOrder=1L;


    public OrderController(CartItemRepository cartItemRepository, OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }



    @RequestMapping("/orders")
    public String getOrders(Model model){
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }

    @PostMapping("/order")
    public String submitOrder(Model model, RedirectAttributes redirectAttributes) {

        User1 currentUser = userRepository.findByName("John Doe");

        // Retrieve the current order
        Order1 currentOrder = currentUser.getOrders().get(0);

        // If the current order has no items, return back to the cart page
        if (currentOrder.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your cart is empty. Please add some products before checking out.");
            return "redirect:/cart";
        }

        // Proceed with the rest of the order submission process only if the order is not empty
        Order1 newOrder = new Order1();
        newOrder.setUser(currentUser);
        for (CartItem item : currentOrder.getItems()) {
            CartItem newItem = new CartItem();
            newItem.setProduct(item.getProduct());
            newItem.setQuantity(item.getQuantity());
            newItem.setOrder(newOrder);
            newOrder.getItems().add(newItem);
        }

        newOrder.setTotalPrice(currentOrder.getTotalPrice());
        Order1 savedOrder = orderRepository.save(newOrder);

        currentOrder.clearItems();
        orderRepository.save(currentOrder);

        model.addAttribute("order", savedOrder);
        return "confirmation";
    }




}
