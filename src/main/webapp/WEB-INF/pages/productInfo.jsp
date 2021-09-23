<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="recentlyViewed" type="com.es.phoneshop.model.viewsHistory.UserViewsHistory" scope="session"/>
<tags:master pageTitle="Product Info">
  <table id="shell">
    <tr>
      <!--
      Main content
    -->
      <td id="shell-td" rowspan="2" style="width:80%">

        <table id="shell">
          <tr>
            <td style="width:200px;">
              <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${product.imageUrl}"/>
            </td>
            <td>
              <h3>
                ${ product.description } (${product.code})
              </h3>

              <table>
                <tr>
                  <td>
                    Cost
                  </td>
                  <td>
                    <a href="${pageContext.servletContext.contextPath}/products/history/${product.id}">
                      <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </a>
                  </td>
                </tr>
                <tr>
                  <td>
                    Code
                  </td>
                  <td>
                    ${product.code}
                  </td>
                </tr>
                <tr>
                  <td>
                    Stock
                  </td>
                  <td>
                    ${product.stock}
                  </td>
                </tr>
              </table>

              <br/>
              <br/>
               ${product.stock > 0? 'Available now!' : 'Unavailable' }
              <form method="post" style="width:200px">
                <p>
                <input name="quantity" value="${not empty param.quantity? param.quantity : '1'}" style="width:100%;"/>
              </p>
                <p>
                  <button id="addToCart">Add to cart</button>
                </p>
              </form>

              <c:if test="${not empty error}">
                <p id="errorInfo">
                  ${error}
                </p>
              </c:if>

              <c:if test="${not empty success}">
                <p id="successInfo">
                  ${success}
                </p>
              </c:if>

            </td>
          </tr>
        </table>

        <br/>
        <br/>
        <br/>
        <br/>
        <br/>

      </td>
      <td id="menu" bgcolor="#bcd1e7" align="center">
        <tags:menu/>
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
