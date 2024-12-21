package pages.juiceShop;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import stringUtils.Generate;

import java.util.List;

public class RegistrationPage extends LoginPage {


    @FindBy(css = "app-register input.mat-input-element")
    List<WebElement> listInputFields;

    @FindBy(css = "app-register input.mat-input-element")
    List<WebElement> listTextValidationError;

    @FindBy(css = "input#emailControl")
    WebElement inputEmail;

    @FindBy(css = "input#passwordControl")
    WebElement inputPassword;

    @FindBy(css = "input#repeatPasswordControl")
    WebElement inputRepeatPassword;

    @FindBy(css = "mat-slide-toggle[id*=mat-slide-toggle]")
    WebElement togglePasswordAdvice;

    @FindBy(css = "[name=securityQuestion]")
    WebElement dropdownSecurityQuestion;

    @FindBy(css = "mat-option>span")
    List<WebElement> optionsSecurityQuestions;

    @FindBy(css = "input#securityAnswerControl")
    WebElement inputSecurityAnswer;

    @FindBy(css = "button#registerButton")
    WebElement buttonRegister;

    @FindBy(css = "span.mat-simple-snack-bar-content")
    WebElement textSuccessMessage;


    protected void verifyInputValidationText() {
        for (int i = 0; i < listInputFields.size(); i++) {
            clickOn(listInputFields.get(i));
            keyBoardActions(Keys.TAB);
            Assert.assertTrue(listTextValidationError.get(i).isDisplayed(), "validation error is not displayed.");
        }
    }

    protected void fillUserRegistrationForm(String username, String password, int questionNumber, String securityAnswer) {

        sendKeys(inputEmail, username);
        sendKeys(inputPassword, password);
        sendKeys(inputRepeatPassword, password);
        clickOn(togglePasswordAdvice);
        clickOn(dropdownSecurityQuestion);
        waitTillVisible(optionsSecurityQuestions.get(0));
        clickOn(optionsSecurityQuestions.get(questionNumber-1));
        sendKeys(inputSecurityAnswer, securityAnswer);
    }

    protected void submitRegistrationForm() {
        clickOn(buttonRegister);
        waitTillVisible(textSuccessMessage);
    }



}
