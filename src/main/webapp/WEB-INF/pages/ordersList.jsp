<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="ordersList" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewed" type="com.es.phoneshop.model.viewsHistory.UserViewsHistory" scope="session"/>
<tags:master pageTitle="My orders">
  <table id="shell">
    <tr>
      <!-- Main content -->
      <td id="shell-td" rowspan="2" style="width:80%">

        <h3 align="center">My Orders</h3>

          <table style="width:100%;">
            <thead>
              <tr>
                <td style="width:64px;">
                  Order ID
                </td>
                <td>
                  Total cost
                </td>
                <td>
                  Number of products
                </td>
                <td>
                  Date
                </td>
              </tr>
            </thead>
            <c:forEach var="order" items="${ordersList}">
              <tr>
                <td>
                  <a href="${pageContext.servletContext.contextPath}/products/order/overview/${order.secureId}">
                    ${order.secureId}
                  </a>
                </td>
                <td>
                  ${order.subTotal} $
                </td>
                <td>
                  ${order.totalQuantity}
                </td>
                <td>
                  ${order.deliveryDate}
                </td>
                <td>

                </td>
              </tr>
            </c:forEach>

          </table>
          <br />

        <br/>

      </td>
      <td>
        <table>
          <tr>
            <td id="menu" bgcolor="#dde2e7" align="center">
                <tags:menu/>
              <br/>
            </td>
          </tr>
          <tr>
            <td bgcolor="#dde2e7" align="center">
              <jsp:include page="/products/cart/miniCart"/>
            </td>
          </tr>
          <tr>
            <td id="shell-td" bgcolor="#dde2e7" align="center">
              <%@ include file="recent.html"%>
            </td>
          </tr>
        </table>
      </td>

    </table>

    <%@ include file="antiheader.html"%>
    </tags:master>
