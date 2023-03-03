package ua.vitaminka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vitaminka.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
