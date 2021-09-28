<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="session"/>
<jsp:useBean id="recentlyViewed" type="com.es.phoneshop.model.viewsHistory.UserViewsHistory" scope="session"/>
<tags:master pageTitle="My cart">
  <table id="shell">
    <tr>
      <!-- Main content -->
      <td id="shell-td" rowspan="2" style="width:80%">

        <form method="get">
          <input name="updating" value="true" type="hidden"/>
          <button id="addToCart">Update</button>
          <br />
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
            <c:forEach var="item" items="${cart.items}">
              <tr>
                <td>
                  <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${item.product.imageUrl}"></td>
                  <td>
                    <a href="${pageContext.servletContext.contextPath}/products/info/${item.product.id}">
                      ${item.product.description}
                    </a>
                  </td>
                  <td>
                    <input name="quantity" value="${item.quantity}"/>
                    <c:if test="${not empty errors[item.product.id]}">
                      <p id="errorInfo">
                        ${errors[item.product.id]}
                      </p>
                    </c:if>
                    <input name="productId" value="${item.product.id}" type="hidden"/>
                  </td>
                  <td>
                    <a href="${pageContext.servletContext.contextPath}/products/history/${item.product.id}">
                      <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                    </a>
                  </td>
                  <td>
                    <button formaction="${pageContext.servletContext.contextPath}/products/cart/deleteCartItem/${item.product.id}" form="deleteCartItem">
                      Delete
                    </button>
                  </td>
              </tr>
            </c:forEach>
            <tr>
              <td colspan="2">
                Summary
              </td>
              <td>
                ${cart.totalQuantity}
              </td>
              <td>
                ${cart.totalCost} $
              </td>
            </tr>
          </table>
        </form>
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
              Place for minicart
            </td>
          </tr>
          <tr>
            <td id="shell-td" bgcolor="#e9eef1" align="center">
              <%@ include file="recent.html"%>
            </td>
          </tr>
        </table>
      </td>

    </table>

    <%@ include file="antiheader.html"%>
    </tags:master>
