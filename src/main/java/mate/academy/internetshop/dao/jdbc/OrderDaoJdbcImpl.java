package mate.academy.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static String ORDERS_TABLE_NAME = "test.orders";
    private static String ORDERS_ITEMS_TABLE_NAME = "test.orders_items";
    private static String ITEMS_TABLE_NAME = "test.items";

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) {
        String query = String.format("INSERT INTO %s (user_id) VALUES (?);", ORDERS_TABLE_NAME);

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getUser());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = String.format("SELECT o.user_id, o.order_id, i.item_id, i.name, i.price "
                        + "FROM %s o "
                        + "JOIN %s oi ON oi.order_id = o.order_id "
                        + "JOIN %s i on oi.item_id = i.item_id AND o.order_id = ?;",
                ORDERS_TABLE_NAME, ORDERS_ITEMS_TABLE_NAME, ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order order = new Order();
            order.setId(id);
            while (resultSet.next()) {
                if (order.getUser() == null) {
                    order.setUser(resultSet.getLong("user_id"));
                }
                Item item = new Item();
                item.setId(resultSet.getLong("item_id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(BigDecimal.valueOf(resultSet.getLong("price")));
                order.getItems().add(item);
            }
            return Optional.of(order);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Order> getAll() {
        String query = String.format("SELECT * FROM %s;", ORDERS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                orders.add(get(orderId).get());
            }
            return orders;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Order update(Order order) {
        String query = String.format("DELETE FROM %s WHERE order_id = ?;",
                ORDERS_ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, order.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException();
        }

        query = String.format("INSERT INTO %s (order_id, item_id) VALUES (?, ?);",
                ORDERS_ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Item item : order.getItems()) {
                preparedStatement.setLong(1, order.getId());
                preparedStatement.setLong(2, item.getId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return order;
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format("DELETE FROM %s WHERE order_id = ?;",
                ORDERS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    public boolean delete(Order order) {
        return delete(order.getId());
    }
}
