package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        return null;
    }

    private List<Item> getItems(List<String> barcodes) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        List<Item> items = new ArrayList<>();
        List<String> distinct = barcodes.stream()
                                .distinct()
                                .collect(Collectors.toList());
        for (String distinctItem : distinct) {
            for (ItemInfo itemInfo: itemInfos) {
                if (itemInfo.getBarcode().equals(distinctItem)){
                    items.add(new Item(itemInfo.getName(),
                            Collections.frequency(barcodes, distinctItem),
                            itemInfo.getPrice(),
                            Collections.frequency(barcodes, distinctItem) * itemInfo.getPrice()));
                }
            }
        }
        return items;
    }
}
