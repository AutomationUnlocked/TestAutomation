package pages.juiceShop;

import logging.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderSummaryPage extends PaymentPage {


    @FindBy(css = "button#checkoutButton")
    private WebElement buttonPlaceOrder;

    protected void placeOrder() {
        Log.info("Place Order");
        clickOn(buttonPlaceOrder);
    }

}
