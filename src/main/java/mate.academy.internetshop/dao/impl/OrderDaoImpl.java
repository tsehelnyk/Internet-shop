package mate.academy.internetshop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {

    private static Long idCount = 0L;

    @Override
    public Order create(Order order) {
        order.setId(idCount);
        idCount++;
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Order get(Long id) {
        return Storage.orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("id " + id + " does not exist."));
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(Storage.orders);
    }

    @Override
    public Order update(Order order) {
        Order oldOrder = get(order.getId());
        Storage.orders.set(Storage.orders.indexOf(oldOrder), order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        Storage.orders.remove(get(id));
        return true;
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }
}
