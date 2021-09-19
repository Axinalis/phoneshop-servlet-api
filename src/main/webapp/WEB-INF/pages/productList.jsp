<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewed" type="com.es.phoneshop.model.viewsHistory.UserViewsHistory" scope="session"/>
<tags:master pageTitle="Product List">
  <table id="shell">
    <tr>
      <!--
      Main content
    -->
      <td id="shell-td" rowspan="2" style="width:80%">

        <table  bgcolor="#eef3f9" style="width:100%;">
          <thead>
            <tr>
              <td>Image</td>
              <td>
                Description
                <tags:sortLink field="description" order="asc"/>
            <tags:sortLink field="description" order="desc"/>
          </td>
              <td class="price">
                Price
                <tags:sortLink field="price" order="asc"/>
            <tags:sortLink field="price" order="desc"/>
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
      <td id="menu" bgcolor="#bcd1e7" align="center">
          <tags:menu listIsActive="false" cartIsActive="true"/>
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
