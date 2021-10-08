<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Advanced search">
  <p>
    <h3 align="center">Advanced search</h3>
  </p>

<form align="center" method="post">
  <table align="center" id="shell" style="width:40%">
    <tr>
      <td>
        Description
      </td>
      <td>
        <input name="description" value="${values['description']}"/>
        <c:if test="${not empty errors['description']}">
          <p id="errorInfo">
            ${errors['description']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        Type of search
      </td>
      <td>
        <select name="paymentType">
          <option>
            All words
          </option>
          <option>
            Any words
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        Min cost
      </td>
      <td>
        <input name="minPrice" value="${values['minPrice']}"/>
        <c:if test="${not empty errors['minPrice']}">
          <p id="errorInfo">
            ${errors['minPrice']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        Max cost
      </td>
      <td>
        <input name="maxPrice" value="${values['maxPrice']}"/>
        <c:if test="${not empty errors['maxPrice']}">
          <p id="errorInfo">
            ${errors['maxPrice']}
          </p>
        </c:if>
      </td>
    </tr>
    <tr>
      <td>
        <button id="supportButton" style="width:100%" formaction="${pageContext.servletContext.contextPath}/products">Back to list</button>
      </td>
      <td>
        <button id="addToCart" style="width:100%">Search</button>
      </td>
    </tr>
  </table>

</form>
</tags:master>
