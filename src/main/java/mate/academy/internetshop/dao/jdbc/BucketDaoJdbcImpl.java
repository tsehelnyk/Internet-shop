package mate.academy.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static String BUCKETS_TABLE_NAME = "test.buckets";
    private static String BUCKETS_ITEMS_TABLE_NAME = "test.buckets_items";
    private static String ITEMS_TABLE_NAME = "test.items";

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        String query = String.format("INSERT INTO %s (user_id) VALUES (?);", BUCKETS_TABLE_NAME);

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, bucket.getUser());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                bucket.setId(resultSet.getLong(1));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long userId) {
        String query = String.format("SELECT b.user_id, b.bucket_id, i.item_id, i.name, i.price " +
                "FROM %s b " +
                "JOIN %s bi ON bi.bucket_id = b.bucket_id " +
                "JOIN %s i on bi.item_id = i.item_id AND b.user_id = ?;",
                BUCKETS_TABLE_NAME, BUCKETS_ITEMS_TABLE_NAME, ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Bucket bucket = new Bucket();
            while (resultSet.next()) {
                if (bucket.getId() == null || bucket.getUser() == null) {
                    bucket.setId(resultSet.getLong("bucket_id"));
                    bucket.setUser(resultSet.getLong("user_id"));
                }
                Item item = new Item();
                item.setId(resultSet.getLong("item_id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(BigDecimal.valueOf(resultSet.getLong("price")));
                bucket.getItems().add(item);
            }
            return Optional.of(bucket);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Bucket update(Bucket bucket) {
        String query = String.format("DELETE FROM %s WHERE bucket_id = ?;",
                BUCKETS_ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucket.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException();
        }

        query = String.format("INSERT INTO %s (bucket_id, item_id) VALUES (?, ?);",
                BUCKETS_ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Item item : bucket.getItems()) {
                preparedStatement.setLong(1, bucket.getId());
                preparedStatement.setLong(2, item.getId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return bucket;
    }

        @Override
    public boolean delete(Long id) {
            String query = String.format("DELETE FROM %s WHERE bucket_id = ?;",
                    BUCKETS_TABLE_NAME);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException();
            }
            return true;
    }

    @Override
    public boolean delete(Bucket bucket) {
        return delete(bucket.getId());
    }

    @Override
    public List<Bucket> getAll() {
        String query = String.format("SELECT * FROM %s;", BUCKETS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Bucket> buckets = new ArrayList<>();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                buckets.add(get(userId).get());
            }
            return buckets;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
