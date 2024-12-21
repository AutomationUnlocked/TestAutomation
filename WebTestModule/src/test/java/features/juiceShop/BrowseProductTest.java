package features.juiceShop;

import org.testng.annotations.Test;
import pages.juiceShop.HomePage;

public class BrowseProductTest extends HomePage {

    @Test(description = "Verify all the products are displayed when the maximum items per page is selected")
    public void verifyAllProducts(){
        navigateToJuiceShop();
        closeWelcomeDialogue();
        selectMaxItemsPerPage();
        verifyAllItemsDisplayed();
    }

    @Test(description = "Verify the product details and open reviews")
    public void verifyProductDetail(){
        navigateToJuiceShop();
        closeWelcomeDialogue();
        selectFirstItem();
        verifyProductDetails("Apple Juice");
        expandReview();
        closeProductDetails();
        sleep(5);
    }

}
