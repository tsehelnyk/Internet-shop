package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;

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
    public Bucket get(Long id) {
        return Storage.buckets.stream()
                .filter(bucket -> bucket.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("id " + id + " does not exist."));
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket oldBucket = get(bucket.getId());
        Storage.buckets.set(Storage.buckets.indexOf(oldBucket), bucket);
        return bucket;
    }

    @Override
    public boolean delete(Long id) {
        Storage.buckets.remove(get(id));
        return true;
    }

    @Override
    public boolean delete(Bucket bucket) {
        return Storage.buckets.remove(bucket);
    }
}
