package pages.juiceShop;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class OrderCompletionPage extends OrderSummaryPage {


    @FindBy(css = "h1.confirmation")
    private WebElement textOrderConfirmation;

    protected void verifyOrderConfirmation() {
        Assert.assertTrue(textOrderConfirmation.isDisplayed());
    }
}


