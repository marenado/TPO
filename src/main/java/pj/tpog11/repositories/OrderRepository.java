package pj.tpog11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.tpog11.model.Order1;

public interface OrderRepository extends JpaRepository<Order1, Long> {


}