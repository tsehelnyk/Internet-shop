package mate.academy.internetshop.dao.jdbc;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.model.Bucket;

public class BucketDaoJDBCImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        return null;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Bucket update(Bucket bucket) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(Bucket bucket) {
        return false;
    }

    @Override
    public List<Bucket> getAll() {
        return null;
    }
}
