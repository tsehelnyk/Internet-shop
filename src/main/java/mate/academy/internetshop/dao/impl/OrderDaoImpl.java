package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;

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
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public Order update(Order order) {
        Order oldOrder = get(order.getId()).get();
        Storage.orders.set(Storage.orders.indexOf(oldOrder), order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(order -> order.getId().equals(id));
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }
}
