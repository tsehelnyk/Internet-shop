package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;

public interface OrderService {
    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    boolean delete(Long id);

    boolean delete(Order order);

    Order completeOrder(List<Item> items, Long userId);

    Optional<List> getUserOrders(Long userId);

}
