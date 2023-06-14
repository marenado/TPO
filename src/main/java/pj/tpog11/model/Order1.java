package pj.tpog11.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User1 user;
    private String status;
    private LocalDateTime orderDateTime;
    private double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    public Order1() {
        this.orderDateTime = LocalDateTime.now();
        this.cartItems = new ArrayList<>();
        this.totalPrice = 0.0;  // Initialize totalPrice to 0
    }

    // Other constructors, getters, setters, etc.

    public void addItem(Product product, int quantity) {
        CartItem cartItem = new CartItem(product, quantity);
        cartItem.setOrder(this);
        cartItems.add(cartItem);
        updateTotalPrice();  // Update the total price after adding an item
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User1 getUser() {
        return user;
    }

    public void setUser(User1 user) {
        this.user = user;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<CartItem> getItems() {
        return cartItems;
    }

    public void setItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    public double getTotalPrice() {  // Getter for totalPrice
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {  // Setter for totalPrice
        this.totalPrice = totalPrice;
    }


    public void removeItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        updateTotalPrice();  // Update the total price after removing an item
    }

    private void updateTotalPrice() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalPrice = total;
    }

    // Other methods related to order operations
}
