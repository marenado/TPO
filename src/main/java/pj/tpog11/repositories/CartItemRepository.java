package pj.tpog11.repositories;
import pj.tpog11.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
