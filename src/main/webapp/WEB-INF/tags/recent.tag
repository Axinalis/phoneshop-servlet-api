<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="recentlyViewed" required="true" %>

<h3>
  Recently viewed
</h3>
<table id="shell">
  <c:forEach var="historyItem" items="${recentlyViewed.recentlyViewed}">
    <tr>
      <td>
        <p align="center">
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${historyItem.imageUrl}">
        </p>
        <p align="center">
          <a href="${pageContext.servletContext.contextPath}/products/info/${historyItem.id}">
            ${historyItem.description}
          </a>
        </p>
        <p align="center">
         <fmt:formatNumber value="${historyItem.price}" type="currency" currencySymbol="${historyItem.currency.symbol}"/>
        </p>
      </td>
    </tr>
  </c:forEach>
</table>
