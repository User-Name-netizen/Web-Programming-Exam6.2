<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Your Cart</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css">
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;600;700&display=swap" rel="stylesheet">
        <style>.product-image{max-width:80px;border-radius:6px}</style>
</head>
<body>
    <div class="page-bg">
        <div class="container">
            <header class="catalog-header"><h1>Your Cart</h1></header>

                        <!-- flash -->
                        <c:if test="${not empty sessionScope.message}">
                                <div class="flash ${sessionScope.messageType}">${sessionScope.message}</div>
                                <c:remove var="message" scope="session" />
                                <c:remove var="messageType" scope="session" />
                        </c:if>

                        <table class="cart-table">
                                <tr>
                                        <th>Quantity</th>
                                        <th>Description</th>
                                        <th>Price</th>
                                        <th>Amount</th>
                                        <th>Remove</th>
                                </tr>
                                <c:forEach var="item" items="${cart.items}">
                                        <tr>
                                                <td class="quantity">
                                                        <form action="cart" method="post">
                                                                <input type="hidden" name="productCode" value="${item.product.code}">
                                                                <input type="text" name="quantity" value="${item.quantity}">
                                                                <input type="submit" value="Update">
                                                        </form>
                                                </td>
                                                <td class="description">${item.product.description}</td>
                                                <td class="price">${item.product.priceCurrencyFormat}</td>
                                                <td class="total">${item.totalCurrencyFormat}</td>
                                                <td class="remove">
                                                        <form action="cart" method="post">
                                                                <input type="hidden" name="productCode" value="${item.product.code}">
                                                                <input type="hidden" name="quantity" value="0">
                                                                <input type="submit" value="Remove Item">
                                                        </form>
                                                </td>
                                        </tr>
                                </c:forEach>
                                <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td><b>Total:</b></td>
                                        <td class="total">${cart.totalCurrencyFormat}</b></td>
                                        <td>&nbsp;</td>
                                </tr>
                        </table>

                                                <div class="cart-note">
                                                        <p><b>To change the quantity, enter the new quantity and click the Update button.</b></p>
                                                </div>

                                                <div class="cart-actions">
                                                        <form action="cart" method="post" style="display:inline-block;margin-right:12px;">
                                                                <input type="hidden" name="action" value="Continue Shopping">
                                                                <input class="secondary" type="submit" value="Continue Shopping">
                                                        </form>

                                                        <form action="cart" method="post" style="display:inline-block;">
                                                                <input type="hidden" name="action" value="Checkout">
                                                                <input type="submit" value="Checkout">
                                                        </form>
                                                </div>
                </section>
        </div>
    </div>
</body>
</html>