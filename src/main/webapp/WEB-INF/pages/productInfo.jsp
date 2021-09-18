<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="Product Info">
  <p>
  ${ product.description } (${product.code})
  </p>

  <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${product.imageUrl}"/>
  <br/>
  <br/>
   Cost:
    <a href="${pageContext.servletContext.contextPath}/products/history/${product.id}">
      <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
    </a>

   <br/>
   <br/>
   ${product.stock > 0? 'Available now!' : 'Unavailable' }

   <form method="post">
     <input name="quantity"/>
     <br/>
     <button>Add to cart</button>
   </form>

   <br/>
   <br/>
   <br/>
   <br/>

   <p>
     <a href="${pageContext.servletContext.contextPath}/products/cart">To my cart</a>
   </p>
   <p>
     <a href="${pageContext.servletContext.contextPath}/products">Back to products list</a>
   </p>


</tags:master>
