package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {

    private static Long idCount = 0L;

    @Override
    public User create(User user) {
        user.setId(idCount);
        idCount++;
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.of(Storage.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("id " + id + " does not exist.")));
    }

    @Override
    public User update(User user) {
        User oldUser = get(user.getId()).get();
        Storage.users.set(Storage.users.indexOf(oldUser), user);
        return user;
    }

    @Override
    public boolean delete(Long id) {
        Storage.users.remove(get(id));
        return true;
    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }
}
