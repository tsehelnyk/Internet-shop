package mate.internetshop.db;

import java.util.ArrayList;
import java.util.List;

import mate.internetshop.model.Bucket;
import mate.internetshop.model.Item;
import mate.internetshop.model.Order;
import mate.internetshop.model.User;

public class Storage {
    public static final List<User> users = new ArrayList<>();
    public static final List<Item> items = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
}
