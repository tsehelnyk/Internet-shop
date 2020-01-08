package mate.internetshop.lib;

import java.util.HashMap;
import java.util.Map;

import mate.internetshop.factory.Factory;
import mate.internetshop.service.BucketService;
import mate.internetshop.service.ItemService;
import mate.internetshop.service.OrderService;
import mate.internetshop.service.UserService;
import mate.internetshop.dao.BucketDao;
import mate.internetshop.dao.ItemDao;
import mate.internetshop.dao.OrderDao;
import mate.internetshop.dao.UserDao;

public class AnnotatedClassMap {
    private static final Map<Class, Object> classMap = new HashMap<>();

    static  {
        classMap.put(BucketDao.class, Factory.getBucketDao());
        classMap.put(BucketService.class, Factory.getBucketService());
        classMap.put(ItemDao.class, Factory.getItemDao());
        classMap.put(ItemService.class, Factory.getItemService());
        classMap.put(OrderDao.class, Factory.getOrderDao());
        classMap.put(OrderService.class, Factory.getOrderService());
        classMap.put(UserDao.class, Factory.getUserDao());
        classMap.put(UserService.class, Factory.getUserService());
    }

    public static Object getImplementation(Class interfaceClass) {
        return classMap.get(interfaceClass);
    }
}
