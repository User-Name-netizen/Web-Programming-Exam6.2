<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List,java.util.ArrayList,murach.business.ProductIO,murach.business.Product" %>

<%
    // If the servlet didn't set products (e.g. page requested directly), populate from ProductIO
    if (request.getAttribute("products") == null) {
        List<Product> productList = new ArrayList<>();
        for (String code : ProductIO.getProductCodes()) {
            productList.add(ProductIO.getProduct(code));
        }
        request.setAttribute("products", productList);
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>CD list</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;600;700&display=swap" rel="stylesheet">
        <style>/* small inline safety to make sure images don't overflow */
            .product-image{max-width:120px;height:auto;border-radius:8px}
        </style>
</head>
<body>
    <div class="page-bg">
        <div class="container">
            <header class="catalog-header">
                <h1>CD list</h1>
            </header>

                        <!-- flash message -->
                        <c:if test="${not empty sessionScope.message}">
                                <div class="flash ${sessionScope.messageType}">${sessionScope.message}</div>
                                <%-- clear message after showing --%>
                                <c:remove var="message" scope="session" />
                                <c:remove var="messageType" scope="session" />
                        </c:if>

                        <!-- debug: show how many products are available (will be removed later) -->
                        <div class="catalog-meta">
                            <span class="products-available">Products available: <strong>${fn:length(products)}</strong></span>
                        </div>

            <section class="catalog">
                <table class="catalog-table">
                    <tr>
                        <th>Description</th>
                        <th class="price">Price</th>
                        <th>&nbsp;</th>
                    </tr>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td class="description">${product.description}</td>
                            <td class="price">${product.priceCurrencyFormat}</td>
                            <td class="add-to-cart">
                                <form action="cart" method="post">
                                    <input type="hidden" name="productCode" value="${product.code}" />
                                    <input type="submit" value="Add to Cart" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </section>
        </div>
    </div>
</body>
</html>