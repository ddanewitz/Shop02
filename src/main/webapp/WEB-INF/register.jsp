<div id="indexRightColumn">
    Register:
    <div id="message">
        ${message} 
    </div>
    <br>
    <form name="login" method="post" action="register">
        
        Name: <input type="text" name="name" maxlength="20" size="17">
        Email: <input type="text" name="email" maxlength="20" size="17"><br><br>
        Lieferadresse: <br>
        <br> Strasse und Hausnr: <input type="text" name="lieferadresse_str" maxlength="20" size="17">
        Stadt: <input type="text" name="lieferadresse_stadt" maxlength="20" size="17"><br><br>
        Passwort: <input type=password1 name="password1" maxlength="20" size="17"><br><br>
        Passwort wiederholen: <input type=password2 name="password2" maxlength="20" size="17"><br><br>
        <input type="checkbox" name="sameAddress"> Lieferadresse wie Rechnungsadresse
        <br><br>
        Rechnungsadresse: <br>
        <br> Strasse und Hausnr: <input type="text" name="rechnungsadresse_str" maxlength="20" size="17">
        Stadt: <input type="text" name="rechnungsadresse_stadt" maxlength="20" size="17"><br>

        <br><input type="SUBMIT" name="Registrieren" value="Registrieren">
    </form>
    
    
</div>