package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) throws DataProcessingException {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) throws DataProcessingException {
        return itemDao.get(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        return itemDao.update(item);
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        return itemDao.delete(id);
    }

    @Override
    public boolean delete(Item item) throws DataProcessingException {
        return itemDao.delete(item);
    }

    @Override
    public List<Item> getAllItems() throws DataProcessingException {
        return itemDao.getAll();
    }
}
