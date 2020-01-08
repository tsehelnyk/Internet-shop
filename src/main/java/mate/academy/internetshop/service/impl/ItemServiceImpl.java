package mate.internetshop.service.impl;

import java.util.List;

import mate.internetshop.dao.ItemDao;
import mate.internetshop.lib.Inject;
import mate.internetshop.lib.Service;
import mate.internetshop.model.Item;
import mate.internetshop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) {
        return itemDao.get(id).get();
    }

    @Override
    public Item update(Item item) {
        return itemDao.update(item);
    }

    @Override
    public boolean delete(Long id) {
        return itemDao.delete(id);
    }

    @Override
    public boolean delete(Item item) {
        return itemDao.delete(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemDao.getAll();
    }
}
