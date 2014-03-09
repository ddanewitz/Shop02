
            
            
            
            <div id="indexRightColumn">
                <c:forEach var="product" items="${shownProducts}" varStatus="iter">
                    <div class="productBox">
                        <br>
                            <span>
                                ${product.name}
                            </span>
                            <br>
                        <a href="<c:url value='productInfo?${product.id}'/>" method="get">
                        <img src="${initParam.productImagePath}${product.name}.png" border="0">
                            
                        </a>
                        <br>
                    </div>
                </tr>
                </c:forEach>
                
                
            </div>
                        
                        
            
            