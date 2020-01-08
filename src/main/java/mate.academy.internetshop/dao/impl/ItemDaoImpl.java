package mate.academy.internetshop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

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
    public Item get(Long id) {
        return Storage.items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("id " + id + " does not exist."));
    }

    @Override
    public Item update(Item item) {
        Item oldItem = get(item.getId());
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
        return new ArrayList<>(Storage.items);
    }
}
