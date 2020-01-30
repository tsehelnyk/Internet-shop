package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.exception.HashGeneratingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) throws DataProcessingException, HashGeneratingException {
        user.setToken(getToken());
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.create(user);
    }

    @Override
    public String getToken() {
        return String.valueOf(UUID.randomUUID());
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        return (id == null) ? Optional.empty() : userDao.get(id);
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        return userDao.getByToken(token);
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        return userDao.getAll();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        return userDao.delete(id);
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        return userDao.delete(user);
    }

    @Override
    public User login(String login, String password)
            throws AuthenticationException, DataProcessingException, HashGeneratingException {
        Optional<User> user = userDao.findByLogin(login);
        if (user.isEmpty() || !checkPassword(user.get(), password)) {
            throw new AuthenticationException("Wrong login or password");
        }
        return user.get();
    }

    @Override
    public boolean checkPassword(User user, String password) throws HashGeneratingException {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
