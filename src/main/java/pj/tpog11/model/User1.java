package pj.tpog11.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    // Constructors, getters, and setters

    public User1() {
    }

    public User1(String name) {
        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order1> orders;

    // getters and setters for orders
    public List<Order1> getOrders() {
        return orders;
    }

    public void setOrders(List<Order1> orders) {
        this.orders = orders;
    }


    public void setName(String name) {
        this.name=name;
    }
}
