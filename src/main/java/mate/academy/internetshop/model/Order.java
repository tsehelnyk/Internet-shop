package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return " " + id;
    }
}
