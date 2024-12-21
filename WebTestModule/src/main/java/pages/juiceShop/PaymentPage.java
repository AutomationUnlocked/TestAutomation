package pages.juiceShop;

import logging.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import stringUtils.Generate;

import java.util.List;

public class PaymentPage extends DeliveryMethodPage {


    @FindBy(css = "app-payment-method mat-expansion-panel-header")
    private WebElement expanderAddNewCard;


    @FindBy(xpath = "//mat-label[text()='Name']/../../..//input")
    private WebElement inputName;


    @FindBy(xpath = "//mat-label[text()='Card Number']/../../..//input")
    private WebElement inputCardNumber;


    @FindBy(xpath = "//mat-label[text()='Expiry Month']/../../..//select")
    private WebElement selectExpiryMonth;


    @FindBy(xpath = "//mat-label[text()='Expiry Year']/../../..//select")
    private WebElement selectExpiryYear;

    @FindBy(css = "button#submitButton")
    private WebElement buttonSubmit;

    @FindBy(css = ".custom-card button")
    private WebElement buttonPayByWallet;

    @FindBy(css = "button.nextButton")
    private WebElement buttonContinue;


    @FindBy(css = "mat-radio-button.mat-radio-button")
    private List<WebElement> radioButtonAvailablePaymentOptions;

    protected void addNewCard() {
        Log.info("Add new card");
        clickOn(expanderAddNewCard);
        sendKeys(inputName, Generate.generateRandomString(8, false));
        sendKeys(inputCardNumber, Generate.generateRandomInteger(16));
        selectByIndex(selectExpiryMonth, 1);
        selectByIndex(selectExpiryYear, 1);
        clickOn(buttonSubmit);
    }

    protected void verifyUnableToPayByWallet() {
//        Assert.assertFalse(isElementClickable(buttonPayByWallet), "The pay by wallet should be disabled.");
    }

    protected void selectPaymentOption(int option) {
        Log.info("select payment option");
        clickOn(radioButtonAvailablePaymentOptions.get(option));
        clickOn(buttonContinue);
    }


}
