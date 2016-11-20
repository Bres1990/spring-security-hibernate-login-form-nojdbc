<%--
  Created by IntelliJ IDEA.
  User: Adam
  Date: 2016-08-05
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>

    <link href="${contextPath}/resources/theme1/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
    </c:if>

    <form method="GET" action="${contextPath}/data" modelAttribute="transferData" id="transferData">
        <h2 class="form-heading">Cash transfer</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input path="firstName" name="firstName" type="text" class="form-control" placeholder="First name"
                   autofocus="true"/>
            <input path="lastName" name="lastName" type="text" class="form-control" placeholder="Last name"/>
            <input path="address" name="address" type="text" class="form-control" placeholder="Address"
                   autofocus="true"/>
            <br/>
            <input path="accountNo" name="accountNo" id="accountNo" type="text" class="form-control" placeholder="Account number"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <span>${error}</span>

            <button name="transfer" class="btn btn-lg btn-primary btn-block" type="submit">Proceed</button>
        </div>

    </form>
</div>
<!-- /container -->
<script>
    window.onload = function() {
        document.getElementById('transferData').onsubmit = function() {
            var account = document.getElementById('accountNo');
            account.value = "11223344";
        };
    };
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/theme1/js/bootstrap.min.js"></script>
</body>
</html>

