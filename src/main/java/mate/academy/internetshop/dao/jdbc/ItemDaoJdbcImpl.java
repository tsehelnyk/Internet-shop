package mate.academy.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoJDBCImpl extends AbstractDao<Item> implements ItemDao {
    private static String DB_NAME = "test";

    public ItemDaoJDBCImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = String.format(Locale.ROOT,
                "INSERT INTO %s.items (`name`, `price`) VALUES ('%s', '%.2f');",
                DB_NAME, item.getName(), item.getPrice());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = String.format("SELECT * FROM %s.items WHERE `id` = '%d';", DB_NAME, id);

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                long itemId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal price = BigDecimal.valueOf(resultSet.getLong("price"));
                Item item = new Item();
                item.setId(itemId);
                item.setName(name);
                item.setPrice(price);
                return Optional.of(item);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format(Locale.ROOT,
                "UPDATE %s.items SET `name` = '%s', `price` = '%.2f' WHERE `id` = '%d';",
                DB_NAME, item.getName(), item.getPrice(), item.getId());

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return item;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format("DELETE FROM %s.items WHERE `id` = '%d'", DB_NAME, id);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean delete(Item item) {
        return delete(item.getId());
    }

    @Override
    public List<Item> getAll() {
        String query = String.format("SELECT * FROM %s.items;", DB_NAME);

        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                long itemId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal price = BigDecimal.valueOf(resultSet.getLong("price"));
                Item item = new Item();
                item.setId(itemId);
                item.setName(name);
                item.setPrice(price);
                items.add(item);
            }
            return items;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
