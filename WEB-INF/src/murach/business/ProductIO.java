package murach.business;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProductIO {
    private static Map<String, Product> products = new HashMap<>();

    static {
        // Hard-code 3 sản phẩm như sách
        Product p1 = new Product();
        p1.setCode("8601");
        p1.setDescription("The band - True Life Songs and Pictures");
        p1.setPrice(14.95);
        products.put("8601", p1);

        Product p2 = new Product();
        p2.setCode("pf");
        p2.setDescription("Paddlefoot - The First CD");
        p2.setPrice(12.95);
        products.put("pf", p2);

        Product p3 = new Product();
        p3.setCode("jr");
        p3.setDescription("Joe Rut - Genuine Wood Grained Finish");
        p3.setPrice(14.95);
        products.put("jr", p3);
    }

    public static Product getProduct(String code) {
        return products.get(code);
    }

    public static Set<String> getProductCodes() {
        return products.keySet();
    }
}