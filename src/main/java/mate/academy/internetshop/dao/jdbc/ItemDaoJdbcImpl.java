package mate.academy.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static String ITEMS_TABLE_NAME = "test.items";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {

        String query = String.format("INSERT INTO %s (name, price) VALUES (?, ?);",
                ITEMS_TABLE_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setBigDecimal(2, item.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                item.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create item: " + e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) throws DataProcessingException {
        String query = String.format("SELECT * FROM %s WHERE item_id = ?;", ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                BigDecimal price = BigDecimal.valueOf(resultSet.getLong("price"));
                Item item = new Item();
                item.setId(itemId);
                item.setName(name);
                item.setPrice(price);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get item: " + e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = String.format("UPDATE %s SET name = ?, price = ? WHERE item_id = ?;",
                ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setBigDecimal(2, item.getPrice());
            preparedStatement.setLong(3, item.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update item: " + e);
        }
        return item;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String query = String.format("DELETE FROM %s WHERE item_id = ?", ITEMS_TABLE_NAME, id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            return rows > 0 ? true : false;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete item: " + e);
        }
    }

    @Override
    public boolean delete(Item item) throws DataProcessingException {
        return delete(item.getId());
    }

    @Override
    public List<Item> getAll() throws DataProcessingException {
        String query = String.format("SELECT * FROM %s;", ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                BigDecimal price = BigDecimal.valueOf(resultSet.getLong("price"));
                Item item = new Item();
                item.setId(itemId);
                item.setName(name);
                item.setPrice(price);
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all item: " + e);
        }
    }
}
