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
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.exception.DataProcessingException;
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
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String query = String.format("INSERT INTO %s (user_id) VALUES (?);", BUCKETS_TABLE_NAME);

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, bucket.getUser());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                bucket.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create bucket: " + e);
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long userId) throws DataProcessingException {
        String query = String.format("SELECT b.user_id, b.bucket_id, i.item_id, i.name, i.price "
                        + "FROM %s b "
                        + "LEFT JOIN %s bi ON bi.bucket_id = b.bucket_id "
                        + "LEFT JOIN %s i on bi.item_id = i.item_id WHERE b.user_id = ?;",
                BUCKETS_TABLE_NAME, BUCKETS_ITEMS_TABLE_NAME, ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Bucket bucket = new Bucket();
            while (resultSet.next()) {
                if (resultSet.getLong("bucket_id") < 1) {
                    return Optional.empty();
                }
                if (bucket.getId() == null || bucket.getUser() == null) {
                    bucket.setId(resultSet.getLong("bucket_id"));
                    bucket.setUser(resultSet.getLong("user_id"));
                }
                if (resultSet.getLong("item_id") > 0) {
                    Item item = new Item();
                    item.setId(resultSet.getLong("item_id"));
                    item.setName(resultSet.getString("name"));
                    item.setPrice(BigDecimal.valueOf(resultSet.getLong("price")));
                    bucket.getItems().add(item);
                }

            }
            return Optional.of(bucket);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get bucket: " + e);
        }
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        String query = String.format("DELETE FROM %s WHERE bucket_id = ?;",
                BUCKETS_ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucket.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete old bucket when update: " + e);
        }

        query = String.format("INSERT INTO %s (bucket_id, item_id) VALUES (?, ?);",
                BUCKETS_ITEMS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Item item : bucket.getItems()) {
                preparedStatement.setLong(1, bucket.getId());
                preparedStatement.setLong(2, item.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update bucket: " + e);
        }
        return bucket;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String query = String.format("DELETE FROM %s WHERE bucket_id = ?;", BUCKETS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            return rows > 0 ? true : false;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete bucket: " + e);
        }

    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        return delete(bucket.getId());
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        String query = String.format("SELECT * FROM %s;", BUCKETS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Bucket> buckets = new ArrayList<>();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                buckets.add(get(userId).get());
            }
            return buckets;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all buckets: " + e);
        }
    }
}
