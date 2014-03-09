<div id="indexRightColumn">
    
        
        
    <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">
        <c:set var="product" value="${cartItem.product}"/>
        <div class="productCartBox">
            <br>
                <span>
                    ${product.name}
                </span>
                <br>
            <a href="<c:url value='productInfo?${product.id}'/>" method="get">
            <img src="${initParam.productImagePath}${product.name}.png" border="0">

            </a>
            <br>
            
            <form action="<c:url value='updateCart'/>" method="post">
                <input type="hidden"
                       name="productId"
                       value="${product.id}">
                <input type="text"
                       maxlength="2"
                       size="2"
                       value="${cartItem.quantity}"
                       name="quantity"
                       style="margin:5px">
                <input type="submit"
                       name="submit"
                       value="update">
            </form>

        </div>
    </tr>
    </c:forEach>
    
    <form name="form1" method="post" action="order">
        <br><br><input type="SUBMIT" name="Buy" value="Bestellen">
    </form>

</div>