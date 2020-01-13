package mate.academy.internetshop.dao.impl;

import java.util.List;
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
        return Storage.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        User oldUser = get(user.getId()).get();
        Storage.users.set(Storage.users.indexOf(oldUser), user);
        return user;
    }

    @Override
    public boolean delete(Long id) {
        User user = get(id).get();
        return user != null ? Storage.users.remove(user) : false;
    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }
}
