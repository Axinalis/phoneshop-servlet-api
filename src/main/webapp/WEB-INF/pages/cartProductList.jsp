<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cartList" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewed" type="com.es.phoneshop.model.viewsHistory.UserViewsHistory" scope="session"/>
<tags:master pageTitle="My cart">
  <table id="shell">
    <tr>
      <!--
      Main content
    -->
      <td id="shell-td" rowspan="2" style="width:80%">

        <table bgcolor="#dfe9f4" style="width:100%;">
          <thead>
            <tr>
              <td>
                Image
              </td>
              <td>
              	Description
              </td>
              <td class="price">
                Quantity
              </td>
              <td>
              	Price
              </td>
            </tr>
          </thead>
          <c:forEach var="item" items="${cartList}">
            <tr>
              <td>
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${item.product.imageUrl}">
              </td>
              <td>
                <a href="${pageContext.servletContext.contextPath}/products/info/${item.product.id}">
                  ${item.product.description}
                </a>
              </td>
              <td>
                ${item.quantity}
              </td>
              <td class="price">
                <a href="${pageContext.servletContext.contextPath}/products/history/${item.product.id}">
                  <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                </a>
              </td>
            </tr>
          </c:forEach>
        </table>

      </td>
      <td id="menu" bgcolor="#bcd1e7" align="center">
        <tags:menu listIsActive="true" cartIsActive="false"/>
        <br/>
      </td>
    </tr>
    <tr>
      <td id="shell-td" bgcolor="#cfdeee" align="center">
        <%@ include file="recent.html"%>
      </td>
    </tr>
  </table>

    <%@ include file="antiheader.html"%>
</tags:master>
