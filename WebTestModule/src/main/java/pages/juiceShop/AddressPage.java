package pages.juiceShop;

import logging.Log;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import stringUtils.Generate;

import java.util.List;

public class AddressPage extends BasketPage {


    @FindBy(css = "button.btn-new-address")
    private WebElement buttonAddNewAddress;

    @FindBy(css = "#address-form input")
    private List<WebElement> inputsNewAddress;

    @FindBy(css = "textarea#address")
    private WebElement textAreaAddress;

    @FindBy(css = "button#submitButton")
    private WebElement buttonSubmit;

    @FindBy(css = "button.btn-next")
    private WebElement buttonContinue;

    @FindBy(css = "simple-snack-bar>span")
    private WebElement popUpNotification;

    @FindBy(css = "mat-radio-button.mat-radio-button")
    private List<WebElement> radioButtonAvailableAddress;

    protected void addNewAddress() {
        Log.info("Add new address");
        waitTillClickable(buttonAddNewAddress);
        clickOn(buttonAddNewAddress);
        sendKeys(inputsNewAddress.get(0), Generate.generateRandomString(8, false));
        sendKeys(inputsNewAddress.get(1), Generate.generateRandomString(8, false));
        sendKeys(inputsNewAddress.get(2), Generate.generateRandomInteger(8));
        sendKeys(inputsNewAddress.get(3), Generate.generateRandomInteger(6));
        sendKeys(textAreaAddress, Generate.generateRandomString(8, false));
        sendKeys(inputsNewAddress.get(4), Generate.generateRandomString(8, false));
        sendKeys(inputsNewAddress.get(5), Generate.generateRandomString(8, false));
        keyBoardActions(Keys.TAB);
        clickOn(buttonSubmit);
        waitTillNotVisible(popUpNotification);
    }

    protected void selectAddress(int option) {
        Log.info("Selecting address");
        waitTillVisible(buttonContinue);
        clickOn(radioButtonAvailableAddress.get(option));
        waitTillClickable(buttonContinue);
        clickOn(buttonContinue);
    }

}
