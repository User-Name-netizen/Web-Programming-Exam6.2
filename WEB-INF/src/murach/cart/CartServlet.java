package murach.cart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import murach.business.Cart;
import murach.business.Product;
import murach.business.ProductIO;

public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/index.jsp";
        String action = request.getParameter("action");
        if (action == null) {
            action = ""; // Default action - determine from parameters
        }
        // Get or create cart in session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        // If the request sent a productCode, treat as add/update/remove depending on quantity
        String code = request.getParameter("productCode");
        String qtyParam = request.getParameter("quantity");
        if (code != null) {
            try {
                String result = "";
                if (qtyParam != null) {
                    int q = Integer.parseInt(qtyParam);
                    if (q < 0) {
                        session.setAttribute("message", "Quantity must be 0 or greater.");
                        session.setAttribute("messageType", "error");
                    } else {
                        result = cart.updateItemQuantity(code, q);
                        if ("removed".equals(result)) {
                            session.setAttribute("message", "Item removed from cart.");
                            session.setAttribute("messageType", "success");
                        } else if ("updated".equals(result)) {
                            session.setAttribute("message", "Item quantity updated.");
                            session.setAttribute("messageType", "success");
                        } else if ("added".equals(result)) {
                            session.setAttribute("message", "Item added to cart.");
                            session.setAttribute("messageType", "success");
                        }
                    }
                } else {
                    // No quantity provided -> add one
                    String r = cart.addItem(code);
                    if ("added".equals(r)) {
                        session.setAttribute("message", "Item added to cart.");
                        session.setAttribute("messageType", "success");
                    } else if ("updated".equals(r)) {
                        session.setAttribute("message", "Item quantity updated.");
                        session.setAttribute("messageType", "success");
                    } else {
                        session.setAttribute("message", "Unable to add item.");
                        session.setAttribute("messageType", "error");
                    }
                }
            } catch (NumberFormatException e) {
                session.setAttribute("message", "Invalid quantity entered.");
                session.setAttribute("messageType", "error");
            }
            session.setAttribute("cart", cart);
        }

        // Handle explicit actions if provided
        if (action.equalsIgnoreCase("Continue Shopping")) {
            url = "/index.jsp";
        } else if (action.equalsIgnoreCase("Checkout")) {
            url = "/checkout.jsp"; // assume exists
        } else {
            // default: show cart page after changes
            url = "/cart.jsp";
        }

        // Forward request
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> productList = new ArrayList<>();
        for (String code : ProductIO.getProductCodes()) {
            productList.add(ProductIO.getProduct(code));
        }
        request.setAttribute("products", productList); // Set vào request scope
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}