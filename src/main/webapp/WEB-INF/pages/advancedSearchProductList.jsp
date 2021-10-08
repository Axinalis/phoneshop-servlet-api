<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <table id="shell">
    <tr>
      <!--
      Main content
    -->
      <td id="shell-td" rowspan="2" style="width:80%">

          <h3 align="center">List of products</h3>

        <table  bgcolor="#f9f9f9" style="width:100%;">
          <thead>
            <tr>
              <td>Image</td>
              <td>
                Description
          </td>
              <td class="price">
                Price
              </td>
            </tr>
          </thead>
          <c:forEach var="product" items="${products}">
            <tr>
              <td>
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${product.imageUrl}">
              </td>
              <td>
                <a href="${pageContext.servletContext.contextPath}/products/info/${product.id}">
                  ${product.description}
                </a>
              </td>
              <td class="price">
              <a href="${pageContext.servletContext.contextPath}/products/history/${product.id}">
                 <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </a>
              </td>
            </tr>
          </c:forEach>
        </table>
      </td>

    </tr>
  </table>

</tags:master>
