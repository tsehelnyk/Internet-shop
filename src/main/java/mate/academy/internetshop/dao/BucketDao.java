package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Bucket;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long userId);

    Bucket update(Bucket bucket);

    boolean delete(Long id);

    boolean delete(Bucket bucket);

    List<Bucket> getAll();
}
