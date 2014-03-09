<%-- 
    Document   : adminlogin
    Created on : 08.03.2014, 21:20:18
    Author     : dima
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        Admin Login:
        <div id="message">
            ${message} 
        </div>
        
        

        <c:choose>
            <c:when test="${admin!=null}">
                Willkommen, Administrator!
                <br><br>
                <form name="adminlogin" method="post" action="adminindex">
                <br><input type="SUBMIT" name="Admin Index" value="Admin Index">
                </form>
                
            </c:when>

            <c:otherwise>
                <form name="adminlogin" method="post" action="adminlogin">
                <br>
                Email: <input type="text" name="username" maxlength="20" size="17">
                Password: <input type=password name="password" maxlength="20" size="17">
                <br><input type="SUBMIT" name="Admin Log in" value="Admin Log in">
                </form>
                
            </c:otherwise>
        </c:choose>

    <br>
    </body>
</html>
