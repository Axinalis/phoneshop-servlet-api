<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product not found error">
  <p>
    <h3>Product is not found</h3>
  </p>

  <p>
    Some error occured
  </p>
</br>
</br>
</br>
</br>
</br>
  <p>
    <a href="${pageContext.servletContext.contextPath}/products">Back to products list</a>
  </p>

</tags:master>
