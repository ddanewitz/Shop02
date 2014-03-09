<div id="indexRightColumn">
    
        
    Zusammenfassung: <br><br>    
    <table style="width:450px">
        <tr>
            <th>
                Rechnungsadresse:
            </th>
        </tr>
        <tr>
          <td>Name:</td>
          <td>${user.name}</td> 
        </tr>
        <tr>
          <td>Straﬂe und Hausnr:</td>
          <td>${user.rechnungsadresseStr}</td> 
        </tr>
        <tr>
          <td>Stadt:</td>
          <td>${user.rechnungsadresseStadt}</td> 
        </tr>
    </table>
        
    <br><br> 
        
    <table style="width:450px">
        <tr>
            <th>
                Lieferadresse:
            </th>
        </tr>
        <tr>
          <td>Name:</td>
          <td>${user.name}</td> 
        </tr>
        <tr>
          <td>Straﬂe und Hausnr:</td>
          <td>${user.lieferadresseStr}</td> 
        </tr>
        <tr>
          <td>Stadt:</td>
          <td>${user.lieferadresseStadt}</td> 
        </tr>
    </table>
    <br><br>     
    
    
    <table style="width:450px">
        <tr>
            <th>
                Artikel:
            </th>
        </tr>

        <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">
            <c:set var="product" value="${cartItem.product}"/>


            <tr>
              <td>${product.name}</td> 
              <td>${cartItem.quantity} Stck.</td> 
              <td>${cartItem.total} &euro;</td> 
            </tr>


        </c:forEach>
    </table>
    <br><br>
    <table style="width:450px">
        <tr>
            <th>
                Gesamtpreis:
            </th>
        </tr>

        
        <tr>
          <td>${cart.total} &euro;</td>
        </tr>
    </table>
    
    

    
    
    
    <form name="form1" method="post" action="confirmOrder">
        <br><br><input type="SUBMIT" name="Buy" value="Kostenpflichtig bestellen">
    </form>

</div>