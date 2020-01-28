package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.model.Order;

public interface OrderDao {
    Order create(Order order) throws DataProcessingException;

    Optional<Order> get(Long id) throws DataProcessingException;

    List<Order> getAll() throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    boolean delete(Long id) throws DataProcessingException;

    boolean delete(Order order) throws DataProcessingException;
}
