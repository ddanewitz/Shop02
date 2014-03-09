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
                    Produkt-ID
                </th>
                <th>
                    Produktname
                </th>
                <th>
                    Produktpreis
                </th>
                <th>
                    Produktbeschreibung
                </th>
                <th>
                    Produkt löschen
                </th>
                
            </tr>
            <c:forEach var="product" items="${allProducts}" varStatus="iter">
                <tr>
                    <td>
                        ${product.id}
                    </td>
                    <td>
                        ${product.name}
                    </td>
                    <td>
                        ${product.price}
                    </td>
                    <td>
                        ${product.description}
                    </td>
                    <td>
                        <form name="deleteProduct" method="post" action="deleteProduct">
                            <input type="hidden" name="productId" value="${product.id}">
                            <br><input type="SUBMIT" name="Löschen" value="Löschen" >
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <form name="changeName" method="post" action="changeProduct">
                            <input type="text" name="value" maxlength="255" size="17">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input type="hidden" name="column" value="name">
                            <br><input type="SUBMIT" name="Speichern" value="Speichern" >
                        </form>
                    
                    </td>
                    <td>
                        <form name="changePrice" method="post" action="changeProduct">
                            <input type="text" name="value" maxlength="255" size="17">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input type="hidden" name="column" value="price">
                            <br><input type="SUBMIT" name="Speichern" value="Speichern" >
                        </form>
                        
                    </td>
                    <td>
                        <form name="changeDescription" method="post" action="changeProduct">
                            <input type="text" name="value" maxlength="255" size="17">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input type="hidden" name="column" value="description">
                            <br><input type="SUBMIT" name="Speichern" value="Speichern" >
                        </form>
                    </td>
                </tr>
            </c:forEach>
            
        </table>
        <br><br>
        <table border="2">
            <tr>
                <th>
                    Produkt Erstellen
                </th>

            </tr>
            <tr>
                <th>
                    Produktname
                </th>
                <th>
                    Produktpreis
                </th>
                <th>
                    Produktbeschreibung
                </th>     
                <th>
                    Kategorien
                </th>  
            </tr>
            <tr>
                <form name="newName" method="post" action="newProduct">
                    <td>
                        <input type="text" name="name" maxlength="255" size="17">
                    </td>
                    <td>
                        <input type="text" name="price" maxlength="255" size="17">
                    </td>
                    <td>
                        <input type="text" name="description" maxlength="255" size="17">
                    </td>
                    <td>
                        <c:forEach var="category" items="${categories}">
                            ${category.name}
                            <input type="checkbox" name="categorySelected${category.id}">
                        </c:forEach>


                        <input type="text" name="description" maxlength="255" size="17">
                        <input type="SUBMIT" name="Produkt erstellen" value="Produkt erstellen" >
                    </td>
                </form>
            </tr>
            
                
            </form>
            
    </body>
</html>
