package pages.juiceShop;

import logging.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DeliveryMethodPage extends AddressPage {


    @FindBy(css = "mat-radio-button.mat-radio-button")
    private List<WebElement> radioButtonsDeliveryMethod;


    @FindBy(css = "button.nextButton")
    private WebElement buttonContinue;

    protected void selectDeliveryMethodAndProceedNext(int option) {
        Log.info("Select Delivery Address");
        waitTillVisible(buttonContinue);
        clickOn(radioButtonsDeliveryMethod.get(option));
        clickOn(buttonContinue);
    }

}
