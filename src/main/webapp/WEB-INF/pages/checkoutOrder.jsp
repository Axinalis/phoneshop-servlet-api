<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="session"/>
<tags:master pageTitle="Checkout">
  <table id="shell">
    <tr>
      <!-- Main content -->
      <td id="shell-td" rowspan="2" style="width:80%">

        <h3 align="center">Checkout order</h3>


          <table bgcolor="#f9f9f9" style="width:100%;">
            <thead>
              <tr>
                <td style="width:64px;">
                  Image
                </td>
                <td>
                  Description
                </td>
                <td>
                  Quantity
                </td>
                <td>
                  Price
                </td>
              </tr>
            </thead>
            <c:forEach var="item" items="${order.items}">
              <tr>
                <td>
                  <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${item.product.imageUrl}"></td>
                  <td>
                    <a href="${pageContext.servletContext.contextPath}/products/info/${item.product.id}">
                      ${item.product.description}
                    </a>
                  </td>
                  <td>
                    ${item.quantity}
                  </td>
                  <td>
                    <a href="${pageContext.servletContext.contextPath}/products/history/${item.product.id}">
                      <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                    </a>
                  </td>
              </tr>
            </c:forEach>
            <tr>
              <td colspan="3">
                Total cost of products
              </td>
              <td>
                ${order.subTotal} $
              </td>
            </tr>
            <tr>
              <td colspan="3">
                Delivery cost
              </td>
              <td>
                ${order.deliveryCost} $
              </td>
            </tr>
            <tr>
              <td colspan="2">
                Summary
              </td>
              <td>
                ${order.totalQuantity}
              </td>
              <td>
                ${order.totalCost} $
              </td>
            </tr>

          </table>
          <br />
          <table style="width:100%;">
            <tr>
              <td>
                <a href="${pageContext.servletContext.contextPath}/products/cart">
                  <button id="supportButton">Back to my cart</button>
                </a>
              </td>
              <td>
                <a href="${pageContext.servletContext.contextPath}/products/order/filling-details">
                  <button id="addToCart">Place an order</button>
                </a>
              </td>
            </tr>
          </table>
        <form id="deleteCartItem" method="post">
        </form>
      </td>
      <td>
        <table>
          <tr>
            <td id="menu" bgcolor="#dde2e7" align="center">
                <tags:menu/>
              <br/>
            </td>
          </tr>
          <tr>
            <td bgcolor="#dde2e7" align="center">
              <jsp:include page="/products/cart/miniCart"/>
            </td>
          </tr>
          <tr>
            <td id="shell-td" bgcolor="#dde2e7" align="center">
              <%@ include file="recent.html"%>
            </td>
          </tr>
        </table>
      </td>

    </table>

    <%@ include file="antiheader.html"%>
    </tags:master>
