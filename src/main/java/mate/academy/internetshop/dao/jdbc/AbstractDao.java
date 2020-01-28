package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;

public class AbstractDao<T> {
//    protected final static String BUCKETS_TABLE_NAME = "test.buckets";
//    protected final static String BUCKETS_ITEMS_TABLE_NAME = "test.buckets_items";
//    protected final static String ORDERS_TABLE_NAME = "test.orders";
//    protected final static String ORDERS_ITEMS_TABLE_NAME = "test.orders_items";
//    protected final static String ITEMS_TABLE_NAME = "test.items";
//    protected final static String USERS_TABLE_NAME = "test.users";
//    protected final static String USERS_ROLES_TABLE_NAME = "test.users_roles";
//    protected final static String ROLES_TABLE_NAME = "test.roles";

    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }
}
