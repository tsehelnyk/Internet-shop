package mate.academy.internetshop.dao;

import java.util.List;

import mate.academy.internetshop.model.Order;

public interface OrderDao {
    Order create(Order order);

    Order get(Long id);

    List<Order> getAll();

    Order update(Order order);

    boolean delete(Long id);

    boolean delete(Order order);
}
