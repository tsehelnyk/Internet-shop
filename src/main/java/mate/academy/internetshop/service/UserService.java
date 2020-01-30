package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.exception.HashGeneratingException;
import mate.academy.internetshop.model.User;

public interface UserService {
    User create(User user) throws DataProcessingException, HashGeneratingException;

    String getToken();

    Optional<User> get(Long id) throws DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;

    List<User> getAll() throws DataProcessingException;

    User update(User user) throws DataProcessingException;

    boolean delete(Long id) throws DataProcessingException;

    boolean delete(User user) throws DataProcessingException;

    User login(String login, String password)
            throws AuthenticationException, DataProcessingException, HashGeneratingException;

    boolean checkPassword(User user, String password) throws HashGeneratingException;
}
