package mate.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.internetshop.dao.ItemDao;
import mate.internetshop.db.Storage;
import mate.internetshop.lib.Dao;
import mate.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {

    private static Long idCount = 0L;

    @Override
    public Item create(Item item) {
        item.setId(idCount);
        idCount++;
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Optional.of(Storage.items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("id " + id + " does not exist.")));
    }

    @Override
    public Item update(Item item) {
        Item oldItem = get(item.getId()).get();
        Storage.items.set(Storage.items.indexOf(oldItem), item);
        return item;
    }

    @Override
    public boolean delete(Long id) {
        Storage.items.remove(get(id));
        return true;
    }

    @Override
    public boolean delete(Item item) {
        return Storage.items.remove(item);
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }
}
