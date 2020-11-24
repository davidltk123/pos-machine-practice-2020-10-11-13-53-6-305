package pos.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ItemDetail> itemDetails = new ArrayList<>();
        itemDetails = generateItemDetails(ItemDataLoader.loadBarcodes());
        String receipt = generateReceipt(itemDetails);
        return receipt;
    }

    private String generateReceipt(List<ItemDetail> itemDetails) {
        String receipt = "***<store earning no money>Receipt***\n";
        for(ItemDetail itemDetail: itemDetails){
            receipt += generateReceiptByItem(itemDetail);
        }
        receipt += "----------------------\n";
        receipt += generateTotal(itemDetails);
        receipt += "**********************";

        return receipt;
    }

    private String generateTotal(List<ItemDetail> itemDetails) {
        int total = 0;
        String totalString="";
        for(ItemDetail itemDetail: itemDetails){
            total += itemDetail.getSubTotal();
        }
        totalString += "Total: "+total+" (yuan)\n";
        return totalString;
    }

    private String generateReceiptByItem(ItemDetail itemDetail) {
        String receiptItem = "";
        receiptItem += "Name: "+itemDetail.getName()+", Quantity: "+itemDetail.getQuantity()+", Unit price: "+itemDetail.getPrice()+" (yuan), Subtotal: "+itemDetail.getSubTotal()+" (yuan)\n";
        return receiptItem;
    }

    private List<ItemDetail> generateItemDetails(List<String> loadBarcodes) {
        List<ItemDetail> itemDetails = new ArrayList<>();

        //create unique barcordes list
        List<String> uniqueBarcodes = new ArrayList<>();
        for(String loadBarcode : loadBarcodes){
            if(!uniqueBarcodes.contains(loadBarcode)){
                uniqueBarcodes.add(loadBarcode);
            }
        }

        //calculate occurence and form itemDetails object
        for(String uniqueBarcode : uniqueBarcodes){
            int occurences = Collections.frequency(loadBarcodes,uniqueBarcode);
            ItemInfo itemInfo = fetchItemInfoFromDatabase(uniqueBarcode);
            ItemDetail itemDetail = new ItemDetail(itemInfo.getName(), occurences, itemInfo.getPrice());
            itemDetails.add(itemDetail);
        }
        return itemDetails;
    }

    private ItemInfo fetchItemInfoFromDatabase(String loadBarcode) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        for(ItemInfo itemInfo : itemInfos){
            if(itemInfo.getBarcode().equals(loadBarcode))
                return itemInfo;
        }
        return null;
    }
}
