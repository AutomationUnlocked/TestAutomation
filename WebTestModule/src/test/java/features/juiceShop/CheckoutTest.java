package features.juiceShop;


import org.testng.Assert;
import org.testng.annotations.Test;
import pages.juiceShop.LoginPage;
import pages.juiceShop.OrderCompletionPage;
import utils.SessionVariables;

public class CheckoutTest extends OrderCompletionPage {


    @Test(description = "Verify successful checkout")
    public void testCheckout() {
        navigateToJuiceShop();
        closeWelcomeDialogue();
        navigateToLoginPage();
//        System.out.println(SessionVariables.getUsername() + " Password " + SessionVariables.getPassword());
        login(SessionVariables.getUsername(),SessionVariables.getPassword());
//        login("QZpZpWAH@abcd.com", ">a4vJLF3");
        addProductsToBasket("Apple Juice", "Carrot", "Banana", "Apple Pomace", "Lemon Juice");
        sleep(10);
        navigateToCart();
        double initialTotalPrice = getTotalPrice();
        increaseQuantity("Apple Juice", 3);
        deleteProduct("Apple Juice");
        double finalTotalPrice = getTotalPrice();
        Assert.assertTrue(finalTotalPrice < initialTotalPrice, "The final price should be lower after deleting");
        checkout();
        addNewAddress();
        selectAddress(0);
        selectDeliveryMethodAndProceedNext(0);
        verifyUnableToPayByWallet();
        addNewCard();
        selectPaymentOption(0);
        placeOrder();
        verifyOrderConfirmation();

    }

}