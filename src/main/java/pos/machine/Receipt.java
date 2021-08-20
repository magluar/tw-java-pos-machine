package pos.machine;

import java.util.List;

public class Receipt {
    private final List<Item> itemDetails;
    private final int totalPrice;

    public Receipt(List<Item> itemDetails, int totalPrice) {
        this.itemDetails = itemDetails;
        this.totalPrice = totalPrice;
    }

    public List<Item> getItemDetails() {
        return itemDetails;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
