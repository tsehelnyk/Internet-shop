package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;

@Dao
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
    public Optional<Bucket> get(Long id) {
        return Storage.buckets.stream()
                .filter(bucket -> bucket.getUser().equals(id))
                .findFirst();
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket oldBucket = get(bucket.getId()).get();
        Storage.buckets.set(Storage.buckets.indexOf(oldBucket), bucket);
        return bucket;
    }

    @Override
    public boolean delete(Long id) {
        Bucket bucket = get(id).get();
        return bucket != null ? Storage.buckets.remove(bucket) : false;
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
