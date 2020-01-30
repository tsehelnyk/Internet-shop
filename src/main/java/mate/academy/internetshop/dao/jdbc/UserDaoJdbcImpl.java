package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static String USERS_TABLE_NAME = "test.users";
    private static String USERS_ROLES_TABLE_NAME = "test.users_roles";
    private static String ROLES_TABLE_NAME = "test.roles";

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String query = String.format("INSERT INTO %s (name, login, password, salt, token) "
                + "VALUES (?, ?, ?, ?, ?);", USERS_TABLE_NAME);

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBytes(4, user.getSalt());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create user: " + e);
        }

        setRolesToDb(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = String.format("SELECT name, login, password, salt, token FROM %s "
                + "WHERE user_id = ?;", USERS_TABLE_NAME);
        User user = new User();
        user.setId(id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setSalt(resultSet.getBytes("salt"));
                user.setToken(resultSet.getString("token"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user: " + e);
        }

        user.setRoles(getRolesFromDb(user));
        return Optional.of(user);
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        String query = String.format("SELECT name, login, password, salt, user_id FROM %s "
                + "WHERE token = ?;", USERS_TABLE_NAME);
        User user = new User();
        user.setToken(token);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setSalt(resultSet.getBytes("salt"));
                user.setId(resultSet.getLong("user_id"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user by token: " + e);
        }

        user.setRoles(getRolesFromDb(user));
        return Optional.of(user);
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        HashMap<Long, User> users = new HashMap<Long, User>();

        String query = String.format("SELECT u.name, u.login, u.password, u.salt, u.token, u.user_id, "
                        + "r.role, r.id "
                        + "FROM %s ur "
                        + "JOIN %s r ON (ur.user_role = r.id) "
                        + "JOIN %s u ON (ur.user_id = u.user_id);",
                USERS_ROLES_TABLE_NAME, ROLES_TABLE_NAME, USERS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (users.containsKey(resultSet.getLong("user_id"))) {
                    Role role = new Role(resultSet.getLong("id"));
                    role.setRoleName(Role.RoleName.valueOf(resultSet.getString("role")));
                    users.get(resultSet.getLong("user_id")).addRole(role);
                } else {
                    User user = new User();
                    user.setLogin(resultSet.getString("login"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setSalt(resultSet.getBytes("salt"));
                    user.setToken(resultSet.getString("token"));
                    user.setId(resultSet.getLong("user_id"));
                    Role role = new Role(resultSet.getLong("id"));
                    role.setRoleName(Role.RoleName.valueOf(resultSet.getString("role")));
                    user.addRole(role);
                    users.put(user.getId(),user);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all users: " + e);
        }

        return users.entrySet().stream()
                .map(user -> user.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public User update(User user) throws DataProcessingException {
        delete(user);

        String query = String.format("INSERT INTO %s (name, login, password, salt, token, user_id) "
                + "VALUES (?, ?, ?, ?, ?, ?);", USERS_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBytes(4, user.getSalt());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update user: " + e);
        }

        setRolesToDb(user);
        return user;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String query = String.format("DELETE FROM %s WHERE user_id = ?;",
                USERS_ROLES_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete user: " + e);
        }
        return true;
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        return delete(user.getId());
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingException {
        String query = String.format("SELECT name, token, password, salt, user_id FROM %s "
                + "WHERE login = ?;", USERS_TABLE_NAME);
        User user = new User();
        user.setLogin(login);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getLong("user_id") > 0) {
                    user.setToken(resultSet.getString("token"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setSalt(resultSet.getBytes("salt"));
                    user.setId(resultSet.getLong("user_id"));
                    user.setRoles(getRolesFromDb(user));
                    return Optional.of(user);
                }

            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find user by login: " + e);
        }

        return Optional.empty();
    }

    private User setRolesToDb(User user) throws DataProcessingException {
        String query = String.format("INSERT INTO %s (user_id, user_role) VALUES (?, ?);",
                USERS_ROLES_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Role role : user.getRoles()) {
                preparedStatement.setLong(1, user.getId());
                if (role.getId() == null) {
                    preparedStatement.setLong(2, getRoleId(role));
                } else {
                    preparedStatement.setLong(2, role.getId());
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to set user roles to database: " + e);
        }
        return user;
    }

    private Set<Role> getRolesFromDb(User user) throws DataProcessingException {
        String query = String.format("SELECT r.id, r.role "
                        + "FROM %s ur "
                        + "JOIN %s r ON ur.user_role = r.id AND ur.user_id = ?;",
                        USERS_ROLES_TABLE_NAME, ROLES_TABLE_NAME);

        try (PreparedStatement preparedStatement
                         = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role(resultSet.getLong("id"));
                role.setRoleName(Role.RoleName.valueOf(resultSet.getString("role")));
                user.addRole(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user roles from database: " + e);
        }
        return user.getRoles();
    }

    private Long getRoleId(Role role) throws DataProcessingException {
        String query = String.format("SELECT id FROM %s WHERE role = ?;", ROLES_TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, role.getRoleName().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                throw new NoSuchElementException();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user role id from database: " + e);
        }
    }
}
