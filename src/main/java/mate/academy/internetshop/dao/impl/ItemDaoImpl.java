package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;

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
    public Optional<Item> get(Long id) {
        return Storage.items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        Item oldItem = get(item.getId()).get();
        Storage.items.set(Storage.items.indexOf(oldItem), item);
        return item;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.items.removeIf(item -> item.getId().equals(id));
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
