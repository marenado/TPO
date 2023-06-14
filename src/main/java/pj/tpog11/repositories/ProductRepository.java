package pj.tpog11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.tpog11.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}