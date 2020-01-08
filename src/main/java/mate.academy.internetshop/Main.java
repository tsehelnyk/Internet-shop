package mate.academy.internetshop;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private static UserService userService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            itemService.create(new Item());
        }
        System.out.println(itemService.getAllItems());
        User user = userService.create(new User());
        user.setName("Some user");
        Bucket bucket = bucketService.create(new Bucket());
        bucket.setUser(user);
        bucket.setItems(itemService.getAllItems());
        Item item = itemService.create(new Item());
        item.setName("some item");
        System.out.println(item.getName() + " " + item.getId());
        bucketService.addItem(bucket, item);
        Order order  = orderService.completeOrder(bucket.getItems(), user);
        System.out.println(order.getUser() + " " + order.getId() + " " + order.getItems());;
    }
}
