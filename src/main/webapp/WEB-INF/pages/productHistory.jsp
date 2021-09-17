<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="Price History">
  <p>
    <h4>${product.description} (${product.code})</h4>
  </p>

  <p>History of prices for this device</p>

  <table>
    <thead>
      <tr>
        <td>
		    Date
		</td>
        <td class="price">
        	Price
        </td>
      </tr>
    </thead>
    <c:forEach var="record" items="${product.priceHistory}">
      <tr>
        <td>
          ${record.date}
        </td>
        <td class="price">
          <fmt:formatNumber value="${record.price}" type="currency" currencySymbol="${record.curr.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>

  <br/>
  <br/>
  <a href="${pageContext.servletContext.contextPath}/products">Back to products list</a>
</tags:master>
