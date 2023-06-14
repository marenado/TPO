package pj.tpog11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.tpog11.model.User1;

public interface UserRepository extends JpaRepository<User1, Long> {
    User1 findByName(String name);
}

