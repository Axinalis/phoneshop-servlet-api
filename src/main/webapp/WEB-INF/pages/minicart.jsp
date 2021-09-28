<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="miniCart" type="java.util.ArrayList" scope="session"/>
Minicart:
<c:forEach var="item" items="${miniCart}">
  ${item.product.description} - ${item.quantity}
</c:forEach>
<c:if test="${miniCartFull eq true}">
  More...
</c:if>
