package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.model.Bucket;

public class BucketDaoImpl implements BucketDao {

    private static Long idCount = 0L;

    @Override
    public Bucket create(Bucket bucket) {
        bucket.setId(idCount);
        idCount++;
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long userId) {
        return Storage.buckets.stream()
                .filter(bucket -> bucket.getUser().equals(userId))
                .findFirst();
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket oldBucket = get(bucket.getUser()).get();
        Storage.buckets.set(Storage.buckets.indexOf(oldBucket), bucket);
        return bucket;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.buckets.removeIf(bucket -> bucket.getId().equals(id));
    }

    @Override
    public boolean delete(Bucket bucket) {
        return Storage.buckets.remove(bucket);
    }

    @Override
    public List<Bucket> getAll() {
        return Storage.buckets;
    }
}
