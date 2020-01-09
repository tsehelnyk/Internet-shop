package mate.academy.internetshop.model;

import java.util.List;

public class Bucket {
    private Long userId;
    private Long id;
    private List<Item> items;

    public Long getUser() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
