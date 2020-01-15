package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface UserService {
    User create(User user);

    String getToken();

    User get(Long id);

    Optional<User> getByToken(String token);

    List<User> getAll();

    User update(User user);

    boolean delete(Long id);

    boolean delete(User user);

    User login(String login, String password) throws AuthenticationException;
}
