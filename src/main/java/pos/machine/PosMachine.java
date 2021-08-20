package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<Item> items = getItems(barcodes);
        Receipt receipt = calculateAmount(items);
        return formatReceipt(receipt);
    }

    private List<Item> getItems(List<String> barcodes) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        List<Item> items = new ArrayList<>();
        List<String> distinct = barcodes.stream()
                .distinct()
                .collect(Collectors.toList());
        for (String distinctItem : distinct) {
            calculateItemSubTotal(barcodes, itemInfos, items, distinctItem);
        }
        return items;
    }

    private void calculateItemSubTotal(List<String> barcodes, List<ItemInfo> itemInfos, List<Item> items, String distinctItem) {
        for (ItemInfo itemInfo : itemInfos) {
            if (itemInfo.getBarcode().equals(distinctItem)) {
                items.add(new Item(itemInfo.getName(),
                        Collections.frequency(barcodes, distinctItem),
                        itemInfo.getPrice(),
                        Collections.frequency(barcodes, distinctItem) * itemInfo.getPrice()));
            }
        }
    }

    private Receipt calculateAmount(List<Item> items) {
        int totalPrice = 0;

        for (Item itemPrice : items) {
            totalPrice += itemPrice.getSubTotal();
        }

        return new Receipt(items, totalPrice);
    }

    private String formatReceipt(Receipt receipt) {
        String formattedReceipt = "***<store earning no money>Receipt***\n";
        int subTotalPrice = 0;
        for (Item item : receipt.getItemDetails()) {
            formattedReceipt = formattedReceipt + "Name: " + item.getName() + ", Quantity: "
                    + item.getQuantity() + ", Unit price: " + item.getUnitPrice()
                    + " (yuan), Subtotal: " + item.getSubTotal() + " (yuan)\n";
            subTotalPrice += item.getSubTotal();
        }

        return formattedReceipt + "----------------------\nTotal: "
                + subTotalPrice + " (yuan)\n**********************";
    }
}
