<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/shop02_3.css">
        <title>JSP Page</title>
    </head>
    <body>
        
      
        <div id="main">
            <div id="header">
                <div id="widgetBar">

                    <div class="headerWidget">
                        <c:choose>
                            <c:when test="${user!=null}">
                                Willkommen, ${user.name}
                            </c:when>

                            <c:otherwise>
                                <a href="login">
                                    [Log in]
                                </a>
                            </c:otherwise>
                      </c:choose>
                    </div>

                    <div class="headerWidget">
                        <c:choose>
                            <c:when test="${cart!=null}">
                                <a href="shoppingCart">
                                    [Warenkorb]
                            </c:when>

                            <c:otherwise>
                                [Warenkorb ist leer]
                            </c:otherwise>
                      </c:choose>
                    </div>
                </div>

                
            </div>
            
            <div id="indexLeftColumn">
                
                

                <c:forEach var="category" items="${categories}">
                    <div class="categoryBox">
                        <a href="category?${category.id}">

                            <span class="categoryLabelText">${category.name}</span>

                        </a>
                    </div>
                </c:forEach>
                
                <form name="form1" method="get" action="searchProduct">
                    <br>
                    <input type="text" name="searchString" maxlength="20" size="17">
                    <br><input type="SUBMIT" name="Suchen" value="Suchen">
                </form>

                
                
                
                
            </div>