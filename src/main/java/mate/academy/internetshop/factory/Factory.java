package mate.internetshop.factory;

import mate.internetshop.dao.impl.BucketDaoImpl;
import mate.internetshop.dao.impl.ItemDaoImpl;
import mate.internetshop.dao.impl.OrderDaoImpl;
import mate.internetshop.dao.impl.UserDaoImpl;
import mate.internetshop.service.BucketService;
import mate.internetshop.service.ItemService;
import mate.internetshop.service.OrderService;
import mate.internetshop.service.UserService;
import mate.internetshop.service.impl.BucketServiceImpl;
import mate.internetshop.service.impl.ItemServiceImpl;
import mate.internetshop.service.impl.OrderServiceImpl;
import mate.internetshop.service.impl.UserServiceImpl;
import mate.internetshop.dao.BucketDao;
import mate.internetshop.dao.ItemDao;
import mate.internetshop.dao.OrderDao;
import mate.internetshop.dao.UserDao;

public class Factory {

    private static BucketDao bucketDaoInstance;
    private static ItemDao itemDaoInstance;
    private static OrderDao orderDaoInstance;
    private static UserDao userDaoInstance;
    private static BucketService bucketServiceInstance;
    private static ItemService itemServiceInstance;
    private static OrderService orderServiceInstance;
    private static UserService userServiceInstance;

    public static BucketDao getBucketDao() {
        if (bucketDaoInstance == null) {
            bucketDaoInstance = new BucketDaoImpl();
        }
        return bucketDaoInstance;
    }

    public static ItemDao getItemDao() {
        if (itemDaoInstance == null) {
            itemDaoInstance = new ItemDaoImpl();
        }
        return itemDaoInstance;
    }

    public static OrderDao getOrderDao() {
        if (orderDaoInstance == null) {
            orderDaoInstance = new OrderDaoImpl();
        }
        return orderDaoInstance;
    }

    public static UserDao getUserDao() {
        if (userDaoInstance == null) {
            userDaoInstance = new UserDaoImpl();
        }
        return userDaoInstance;
    }

    public static BucketService getBucketService() {
        if (bucketServiceInstance == null) {
            bucketServiceInstance = new BucketServiceImpl();
        }
        return bucketServiceInstance;
    }

    public static ItemService getItemService() {
        if (itemServiceInstance == null) {
            itemServiceInstance = new ItemServiceImpl();
        }
        return itemServiceInstance;
    }

    public static OrderService getOrderService() {
        if (orderServiceInstance == null) {
            orderServiceInstance = new OrderServiceImpl();
        }
        return orderServiceInstance;
    }

    public static UserService getUserService() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserServiceImpl();
        }
        return userServiceInstance;
    }
}
