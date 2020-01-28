package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderService {
    Order create(Order order) throws DataProcessingException;

    Order get(Long id) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    boolean delete(Long id) throws DataProcessingException;

    boolean delete(Order order) throws DataProcessingException;

    Order completeOrder(List<Item> items, User user) throws DataProcessingException;

    Optional<List> getUserOrders(User user) throws DataProcessingException;

}
