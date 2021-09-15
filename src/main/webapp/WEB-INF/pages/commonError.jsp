<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product not found error">
  <p>
    Undefined error
  </p>

  <p>
    Some error occured
  </p>
  <p>
    <a href="${pageContext.servletContext.contextPath}/products">Back to products list</a>
  </p>

</tags:master>
