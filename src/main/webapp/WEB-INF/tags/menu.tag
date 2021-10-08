<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3>Menu</h3>
<form action="${pageContext.servletContext.contextPath}/products">
	<input name="query" value="${param.query}" style="width: 80%; border:0px;">
	<button id="search">Search</button>
</form>

<form action="${pageContext.servletContext.contextPath}/products/adv-search">
	<button id="menuButton">Advanced search</button>
</form>
<form action="${pageContext.servletContext.contextPath}/products">
	<button id="menuButton">Product List</button>
</form>
<form action="${pageContext.servletContext.contextPath}/products/cart">
	<button id="menuButton">My Cart</button>
</form>
<form action="${pageContext.servletContext.contextPath}/products/order/overview">
	<button id="menuButton">My Orders</button>
</form>
