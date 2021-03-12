<html>
<body>
<%
session.invalidate();
response.sendRedirect("login.jsp");
%>
</body>
</html>