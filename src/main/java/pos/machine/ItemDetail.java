package pos.machine;

public class ItemDetail {
    private final String name;
    private final int quantity;
    private final int price;
    private final int subTotal;

    public ItemDetail(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = quantity*price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getSubTotal() {
        return subTotal;
    }
}
