<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="session"/>
<jsp:useBean id="paymentTypes" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Order details">
  <p>
    <h3 align="center">Order details</h3>
  </p>

<form align="center" method="post">
  <table align="center" id="shell" style="width:40%">
    <tr>
      <td>
        First name
      </td>
      <td>
        <input name="firstName" value="${values['firstName']}"/>
        <c:if test="${not empty errors['firstName']}">
          <p id="errorInfo">
            ${errors['firstName']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        Last name
      </td>
      <td>
        <input name="lastName"  value="${values['lastName']}"/>
        <c:if test="${not empty errors['lastName']}">
          <p id="errorInfo">
            ${errors['lastName']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        Phone number
      </td>
      <td>
        <input name="phoneNumber" value="${values['phoneNumber']}"/>
        <c:if test="${not empty errors['phoneNumber']}">
          <p id="errorInfo">
            ${errors['phoneNumber']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        Date of delivery
      </td>
      <td>
        <input type="date" name="deliveryDate" value="${values['deliveryDate']}"/>
        <c:if test="${not empty errors['deliveryDate']}">
          <p id="errorInfo">
            ${errors['deliveryDate']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        Address
      </td>
      <td>
        <input name="address" value="${values['address']}"/>
        <c:if test="${not empty errors['address']}">
          <p id="errorInfo">
            ${errors['address']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        Type of payment
      </td>
      <td>
        <select name="paymentType">
          <c:forEach var="type" items="${paymentTypes}">
            <option>
              ${type}
            </option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <button id="supportButton" style="width:100%" formaction="${pageContext.servletContext.contextPath}/products/cart">Back to cart</button>
      </td>
      <td>
        <button id="addToCart" style="width:100%">Place an order</button>
      </td>
    </tr>
  </table>
  <p>
    <br />
  </p>

</form>

  <br/>
  <br/>
  <a href="${pageContext.servletContext.contextPath}/products">Back to products list</a>
</tags:master>
