package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.model.Item;

public interface ItemDao {
    Item create(Item item) throws DataProcessingException;

    Optional<Item> get(Long id) throws DataProcessingException;

    Item update(Item item) throws DataProcessingException;

    boolean delete(Long id) throws DataProcessingException;

    boolean delete(Item item) throws DataProcessingException;

    List<Item> getAll() throws DataProcessingException;
}
