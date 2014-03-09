<%-- 
    Document   : adminproductedit
    Created on : 08.03.2014, 23:32:03
    Author     : dima
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <div id="message">
        ${message} 
    </div>
    
    <body>
        <form name="adminindex" method="post" action="adminindex">
        <br><input type="SUBMIT" name="Admin Index" value="Admin Index">
        </form>
        
        <table border="2">
            <tr>
                <th>
                    Bestellungs-ID
                </th>
                <th>
                    Gesamtpreis
                </th>
                <th>
                    Datum
                </th>
                <th>
                    Bestaetigungsnummer
                </th>
                <th>
                    Benutzer-ID
                </th>
                <th>
                    Status
                </th>
                
            </tr>
            
            <c:forEach var="order" items="${allOrders}" varStatus="iter">
                <tr>
                    <td>
                        ${order.id}
                    </td>
                    
                    <td>
                        ${order.amount}
                    </td>
                    
                    <td>
                        ${order.dateCreated}
                    </td>
                    
                    <td>
                        ${order.confirmationNumber}
                    </td>
                    
                    <td>
                        ${order.customerId}
                    </td>
                    
                    <td>
                        ${order.status}
                    </td>
                    <td>
                        <form name="changeStatus" method="post" action="changeStatus">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <br><input type="SUBMIT" name="Versendet" value="Versendet" >
                        </form>
                    </td>
                </tr>
                
            </c:forEach>
            
        </table>

    </body>
</html>
