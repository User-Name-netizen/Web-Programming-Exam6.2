package murach.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<LineItem> items = new ArrayList<>();

    public Cart() {
        items = new ArrayList<>();
    }

    public List<LineItem> getItems() {
        return items;
    }

    public int getCount() {
        return items.size();
    }

    public String addItem(String code) {
        String result = "error";
        int quantity = 0;

        // Tìm LineItem hiện tại
        for (LineItem item : items) {
            if (item.getProduct().getCode().equals(code)) {
                if (item.getQuantity() == 0) {
                    items.remove(item); // Nếu quantity = 0, remove
                } else {
                    item.setQuantity(item.getQuantity() + 1);
                    result = "updated";
                }
                return result;
            }
        }

        // Thêm LineItem mới nếu không tồn tại
        Product product = ProductIO.getProduct(code);
        if (product != null) {
            LineItem lineItem = new LineItem(product);
            lineItem.setQuantity(1);
            items.add(lineItem);
            result = "added";
        }
        return result;
    }

    public String removeItem(String code) {
        String result = "error";
        for (int i = 0; i < items.size(); i++) {
            LineItem item = items.get(i);
            if (item.getProduct().getCode().equals(code)) {
                items.remove(i);
                result = "removed";
                break;
            }
        }
        return result;
    }

    /**
     * Update quantity for a product. If quantity == 0 remove the item.
     */
    public String updateItemQuantity(String code, int quantity) {
        String result = "error";
        for (int i = 0; i < items.size(); i++) {
            LineItem item = items.get(i);
            if (item.getProduct().getCode().equals(code)) {
                if (quantity <= 0) {
                    items.remove(i);
                    result = "removed";
                } else {
                    item.setQuantity(quantity);
                    result = "updated";
                }
                return result;
            }
        }
        // If not found and quantity > 0, add new item with that quantity
        if (quantity > 0) {
            Product p = ProductIO.getProduct(code);
            if (p != null) {
                LineItem li = new LineItem(p);
                li.setQuantity(quantity);
                items.add(li);
                result = "added";
            }
        }
        return result;
    }

    public double getTotal() {
        double total = 0.0;
        for (LineItem item : items) {
            total += item.getTotal();
        }
        return total;
    }

    public String getTotalCurrencyFormat() {
        java.text.NumberFormat currency = java.text.NumberFormat.getCurrencyInstance();
        return currency.format(getTotal());
    }
}