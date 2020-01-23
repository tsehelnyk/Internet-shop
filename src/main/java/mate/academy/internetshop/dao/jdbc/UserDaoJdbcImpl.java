package mate.academy.internetshop.dao.jdbc;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.model.User;

public class UserDaoJDBCImpl implements UserDao {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.empty();
    }
}
