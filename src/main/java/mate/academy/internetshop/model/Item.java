package mate.academy.internetshop.model;

import java.math.BigDecimal;

public class Item {
    private Long id;
    private String name;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " " + id;
    }

    @Override
    public boolean equals(Object obj) {
        Item item = (Item) obj;
        return this.getId() == item.getId();
    }
}
