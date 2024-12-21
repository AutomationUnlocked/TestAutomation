package pages.juiceShop;

import logging.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import seleniumCore.CorePage;

public class LoginPage extends HomePage {

    @FindBy(css = "div#newCustomerLink")
    WebElement linkNotCustomer;

    @FindBy(css = "input#email")
    WebElement inputEmail;

    @FindBy(css = "input#password")
    WebElement inputPassword;

    @FindBy(css = "button#loginButton")
    WebElement buttonLogin;

    public void navigateToRegistration(){
        clickOn(linkNotCustomer);
    }

    public void login(String username, String password){
        Log.info("logging in");
        sendKeys(inputEmail, username);
        sendKeys(inputPassword,password);
        clickOn(buttonLogin);
    }





}
