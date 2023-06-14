//package pj.tpog11.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import pj.tpog11.model.CartItem;
//import pj.tpog11.model.Order1;
//import pj.tpog11.model.Product;
//import pj.tpog11.model.User1;
//import pj.tpog11.repositories.CartItemRepository;
//import pj.tpog11.repositories.OrderRepository;
//import pj.tpog11.repositories.ProductRepository;
//import pj.tpog11.repositories.UserRepository;
//
//@Controller
//public class CartController {
//
//    private final CartItemRepository cartItemRepository;
//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;
//    private final OrderRepository orderRepository;
//
//    public CartController(CartItemRepository cartItemRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
//        this.cartItemRepository = cartItemRepository;
//        this.productRepository = productRepository;
//        this.userRepository = userRepository;
//        this.orderRepository=orderRepository;
//    }
//
//    @RequestMapping("/cart")
//    public String getCart(Model model){
//        Order1 currentOrder = getCurrentUserOrder();
//        model.addAttribute("cartItems", currentOrder.getItems());
//        model.addAttribute("total", currentOrder.getTotalPrice());
//        return "cart";
//    }
//
//
////    @GetMapping("/cart/add/{productId}")
////    public String addProductToCart(@PathVariable Long productId) {
////        Order1 currentOrder = getCurrentUserOrder();
////        Product product = productRepository.findById(productId).orElse(null);
////
////        if (product != null) {
////            // Find existing cartItem for the product if it exists
////            CartItem existingCartItem = currentOrder.getItems().stream()
////                    .filter(item -> item.getProduct().getId().equals(productId))
////                    .findFirst().orElse(null);
////
////            if (existingCartItem != null) {
////                // If cartItem for the product already exists, increase its quantity
////                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
////                cartItemRepository.save(existingCartItem);
////            } else {
////                // If cartItem for the product doesn't exist, create a new one
////                CartItem cartItem = new CartItem(product, 1); // 1 is the default quantity
////                cartItem.setOrder(currentOrder);
////                currentOrder.getItems().add(cartItem);
////                cartItemRepository.save(cartItem);
////            }
////        }
////
////        return "redirect:/cart";
////    }
//
//    @GetMapping("/cart/remove/{productId}")
//    public String removeProductFromCart(@PathVariable Long productId) {
//        Order1 currentOrder = getCurrentUserOrder();
//        CartItem cartItem = currentOrder.getItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst().orElse(null);
//
//        if (cartItem != null) {
//            currentOrder.getItems().remove(cartItem);
//            cartItemRepository.delete(cartItem);
//        }
//
//        return "redirect:/cart";
//    }
//
//    @PostMapping("/home/adding")
//    public String addProductFromHome(@RequestParam Long productId) {
//        System.out.println("addProductToCart method called with productId: " + productId);
//        Order1 currentOrder = getCurrentUserOrder();
//        Product product = productRepository.findById(productId).orElse(null);
//
//        if (product != null) {
//            // Find existing cartItem for the product if it exists
//            CartItem existingCartItem = currentOrder.getItems().stream()
//                    .filter(item -> item.getProduct().getId().equals(productId))
//                    .findFirst().orElse(null);
//
//            if (existingCartItem != null) {
//                // If cartItem for the product already exists, increase its quantity
//                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
//                cartItemRepository.save(existingCartItem);
//            } else {
//                // If cartItem for the product doesn't exist, create a new one
//                CartItem cartItem = new CartItem(product, 1); // 1 is the default quantity
//                cartItem.setOrder(currentOrder);
//                currentOrder.getItems().add(cartItem);
//                cartItemRepository.save(cartItem);
//            }
//        }
//
//        return "redirect:/"; // Redirect back to the home page
//    }
//
//
//
//    @GetMapping("/cart/reduce/{productId}")
//    public String reduceProductFromCart(@PathVariable Long productId) {
//        Order1 currentOrder = getCurrentUserOrder();
//        CartItem cartItem = currentOrder.getItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst().orElse(null);
//
//        if (cartItem != null) {
//            cartItem.setQuantity(cartItem.getQuantity() - 1); // Reduce the quantity
//
//            // If the quantity is zero, remove the item from cart
//            if (cartItem.getQuantity() == 0) {
//                currentOrder.getItems().remove(cartItem);
//                cartItemRepository.delete(cartItem);
//            } else {
//                // Else, save the updated cart item
//                cartItemRepository.save(cartItem);
//            }
//        }
//
//        return "redirect:/cart";
//    }
//
//
//    private Order1 getCurrentUserOrder() {
//        // Replace with your actual logic to get the current user's order.
//        User1 currentUser = userRepository.findByName("John Doe");  // Placeholder logic
//        return currentUser.getOrders().get(0);  // Assuming that the user has at least one order
//    }
//}


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
        Order1 currentOrder = getCurrentUserOrder();
        model.addAttribute("cartItems", currentOrder.getItems());
        model.addAttribute("total", currentOrder.getTotalPrice());
        return "cart";
    }

    @GetMapping("/home/add/{productId}")
    @ResponseBody
    public Map<String, Object> addProductToCart(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        Order1 currentOrder = getCurrentUserOrder();
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            // Find existing cartItem for the product if it exists
            CartItem existingCartItem = currentOrder.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst().orElse(null);

            if (existingCartItem != null) {
                // If cartItem for the product already exists, increase its quantity
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                cartItemRepository.save(existingCartItem);
            } else {
                // If cartItem for the product doesn't exist, create a new one
                CartItem cartItem = new CartItem(product, 1); // 1 is the default quantity
                cartItem.setOrder(currentOrder);
                currentOrder.getItems().add(cartItem);
                cartItemRepository.save(cartItem);
            }

            // Add the data you want to return to front end
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
            cartItemRepository.delete(cartItem);
        }

        return "redirect:/cart";
    }


    private Order1 getCurrentUserOrder() {
        // Replace with your actual logic to get the current user's order.
        User1 currentUser = userRepository.findByName("John Doe");  // Placeholder logic
        return currentUser.getOrders().get(0);  // Assuming that the user has at least one order
    }
        @GetMapping("/cart/reduce/{productId}")
    public String reduceProductFromCart(@PathVariable Long productId) {
        Order1 currentOrder = getCurrentUserOrder();
        CartItem cartItem = currentOrder.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() - 1); // Reduce the quantity

            // If the quantity is zero, remove the item from cart
            if (cartItem.getQuantity() == 0) {
                currentOrder.getItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {
                // Else, save the updated cart item
                cartItemRepository.save(cartItem);
            }
        }

        return "redirect:/cart";
    }

}
