package mate.internetshop.service.impl;

import java.util.List;
import java.util.Optional;

import mate.internetshop.dao.BucketDao;
import mate.internetshop.lib.Inject;
import mate.internetshop.lib.Service;
import mate.internetshop.model.Bucket;
import mate.internetshop.model.Item;
import mate.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id).get();
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Long id) {
        return bucketDao.delete(id);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return bucketDao.delete(bucket);
    }

    @Override
    public void addItem(Bucket bucket, Item item) {
        bucket.getItems().add(item);
        bucketDao.update(bucket);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        bucket.getItems().remove(item);
        bucketDao.update(bucket);
    }

    @Override
    public void clear(Bucket bucket) {
        bucket.getItems().clear();
        bucketDao.update(bucket);
    }

    @Override
    public Optional<List> getAllItems(Bucket bucket) {
        return Optional.of(bucket.getItems());
    }
}
