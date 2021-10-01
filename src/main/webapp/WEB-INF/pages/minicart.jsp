<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="miniCart" type="java.util.ArrayList" scope="session"/>
<h3>My Cart</h3>
<table id="minicart">
  <c:forEach var="item" items="${miniCart}">
    <tr>
      <td id="minicart-td" style="width: 35px;">
        <img style="max-width: 30px;" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${item.product.imageUrl}">
      </td>
      <td id="minicart-td">
        ${item.product.description}
      </td>
      <td id="minicart-td">
        ${item.quantity}
      </td>
    </tr>


  </c:forEach>
</table>
<c:if test="${miniCartFull eq true}">
  <form action="${pageContext.servletContext.contextPath}/products/cart">
  	<button>More...</button>
  </form>
</c:if>
