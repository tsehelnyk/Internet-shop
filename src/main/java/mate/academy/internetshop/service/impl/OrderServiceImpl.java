package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }

    @Override
    public boolean delete(Order order) {
        return orderDao.delete(order);
    }

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Order order = create(new Order());
        order.setItems(items);
        order.setUser(user.getId());
        update(order);
        return order;
    }

    @Override
    public Optional<List> getUserOrders(User user) {
        Long userId = user.getId();
        return Optional.of(orderDao.getAll().stream()
                .filter(order -> order.getUser().equals(userId))
                .collect(Collectors.toList()));
    }
}
