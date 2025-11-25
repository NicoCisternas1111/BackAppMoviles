package cl.shozoko.shozokoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.shozoko.shozokoapi.model.Order;
import cl.shozoko.shozokoapi.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
