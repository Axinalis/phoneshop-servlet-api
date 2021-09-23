<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="listIsActive" required="true" %>
<%@ attribute name="cartIsActive" required="true" %>

<h3>Menu</h3>
<form action="${pageContext.servletContext.contextPath}/products">
	<input name="query" value="${param.query}" style="width: 80%;">
	<button style="width: 80%;">Search</button>
</form>

	<c:if test="${listIsActive == true}">
		<p align="center">
			<a href="${pageContext.servletContext.contextPath}/products">Products list</a>
		</p>
	</c:if>
	<c:if test="${listIsActive == false}">
		<p align="center">
			Products list
		</p>
	</c:if>

	<c:if test="${cartIsActive eq true}">
		<p align="center">
			<a href="${pageContext.servletContext.contextPath}/products/cart">My cart</a>
		</p>
	</c:if>
	<c:if test="${cartIsActive eq false}">
		<p align="center">
			My cart
		</p>
	</c:if>
