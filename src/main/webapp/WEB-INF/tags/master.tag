<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
  <main>
    <table id="shell" align="center" rules="rows" style="width:60%;">
      <tr>
        <td id="shell-td">
          <table id="shell">
            <tr>
              <th>
                <header>
                  <a href="${pageContext.servletContext.contextPath}">
                    <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
                    PhoneShop
                  </a>
                </header>
              </th>
            </tr>
          </table>
          <jsp:doBody/>
        </td>
      </tr>
    </table>
  </main>
</body>
</html>
