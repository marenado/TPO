
package pj.tpog11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pj.tpog11.model.CartItem;
import pj.tpog11.model.Order1;
import pj.tpog11.model.Product;
import pj.tpog11.model.User1;
import pj.tpog11.repositories.CartItemRepository;
import pj.tpog11.repositories.OrderRepository;
import pj.tpog11.repositories.ProductRepository;
import pj.tpog11.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartController(CartItemRepository cartItemRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @RequestMapping("/cart")
    public String getCart(Model model){

        if (!orderRepository.findAll().isEmpty()) {
            Order1 currentOrder = getCurrentUserOrder();
            model.addAttribute("cartItems", currentOrder.getItems());
            model.addAttribute("total", currentOrder.getTotalPrice());
        } else {

            model.addAttribute("cartItems", new ArrayList<>());
            model.addAttribute("total", 0);
        }

        return "cart";
    }


    @GetMapping("/home/add/{productId}")
    @ResponseBody
    public Map<String, Object> addProductToCart(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        Order1 currentOrder = getCurrentUserOrder();
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {

            CartItem existingCartItem = currentOrder.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst().orElse(null);

            if (existingCartItem != null) {

                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                currentOrder.setTotalPrice(currentOrder.getTotalPrice() + existingCartItem.getProduct().getPrice()); // Increase total price
                cartItemRepository.save(existingCartItem);
            } else {

                CartItem cartItem = new CartItem(product, 1);
                cartItem.setOrder(currentOrder);
                currentOrder.getItems().add(cartItem);
                currentOrder.setTotalPrice(currentOrder.getTotalPrice() + cartItem.getProduct().getPrice());
                cartItemRepository.save(cartItem);
            }


            orderRepository.save(currentOrder);


            response.put("totalItems", currentOrder.getItems().size());
            response.put("totalPrice", currentOrder.getTotalPrice());
        }

        return response;
    }


    @GetMapping("/cart/remove/{productId}")
    public String removeProductFromCart(@PathVariable Long productId) {
        Order1 currentOrder = getCurrentUserOrder();
        CartItem cartItem = currentOrder.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if (cartItem != null) {
            currentOrder.getItems().remove(cartItem);
            currentOrder.setTotalPrice(currentOrder.getTotalPrice() - cartItem.getProduct().getPrice() * cartItem.getQuantity()); // Reduce total price
            cartItemRepository.delete(cartItem);


            orderRepository.save(currentOrder);
        }

        return "redirect:/cart";
    }


    private Order1 getCurrentUserOrder() {

        User1 currentUser = userRepository.findByName("John Doe");

        if (currentUser != null && !currentUser.getOrders().isEmpty()) {

            return currentUser.getOrders().get(0);
        } else {
            Order1 newOrder;
            if (currentUser != null) {
                newOrder = new Order1();
                newOrder.setUser(currentUser);
                newOrder.setItems(new ArrayList<>());
                newOrder.setTotalPrice(0);
                orderRepository.save(newOrder);


                currentUser.getOrders().add(newOrder);
                userRepository.save(currentUser);
            } else {

                newOrder = null;
            }

            return newOrder;
        }
    }


    @GetMapping("/cart/reduce/{productId}")
    public String reduceProductFromCart(@PathVariable Long productId) {
        Order1 currentOrder = getCurrentUserOrder();
        CartItem cartItem = currentOrder.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            currentOrder.setTotalPrice(currentOrder.getTotalPrice() - cartItem.getProduct().getPrice());

            if (cartItem.getQuantity() == 0) {
                currentOrder.getItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {

                cartItemRepository.save(cartItem);
            }

            orderRepository.save(currentOrder);
        }

        return "redirect:/cart";
    }

    @GetMapping("/cart/increase/{productId}")
    public String increaseProductInCart(@PathVariable Long productId) {
        Order1 currentOrder = getCurrentUserOrder();
        CartItem cartItem = currentOrder.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            currentOrder.setTotalPrice(currentOrder.getTotalPrice() + cartItem.getProduct().getPrice());
            cartItemRepository.save(cartItem);
            orderRepository.save(currentOrder);
        }

        return "redirect:/cart";
    }


}
