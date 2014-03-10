<div id="indexRightColumn">
    <div class="productInfoBox">
        <table style="width:300px">
            <tr>
              <td>${selectedProduct.name}</td>
            </tr>
            <tr>
              <td>${selectedProduct.price} &euro; </td>
            </tr>
            <tr>
              <td>${selectedProduct.description}</td>
            </tr>
            <tr>
                <img src="${initParam.productImagePath}${selectedProduct.name}.png" border="0">
            </tr>
            <td>
                <form name="form2" method="post" action="addToCart">
                    <input type="SUBMIT" name="in Warenkorb" value="in Warenkorb">
                    <input type="hidden"
                            name="productId"
                            value="${selectedProduct.id}">

                </form>
            </td>
        </table>
    </div>
    
    

</div>