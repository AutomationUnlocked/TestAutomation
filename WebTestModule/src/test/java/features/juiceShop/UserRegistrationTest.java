package features.juiceShop;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.juiceShop.RegistrationPage;
import stringUtils.Generate;
import utils.SessionVariables;

public class UserRegistrationTest extends RegistrationPage {

    @Test(description = "Verify validation error for input fields")
    public void verifyInputValidationError() {
        navigateToJuiceShop();
        closeWelcomeDialogue();
        navigateToLoginPage();
        navigateToRegistration();
        verifyInputValidationText();
    }



    @Test(description = "Verify Successful registration and login")
    public void verifyRegistrationAndLogin() {
        String username = Generate.generateRandomEmail();
        String password = Generate.generateRandomPassword(8);
        String securityAnswer = Generate.generateRandomString(5, false);
        navigateToJuiceShop();
        closeWelcomeDialogue();
        navigateToLoginPage();
        navigateToRegistration();
        fillUserRegistrationForm(username,password,2,securityAnswer);
        submitRegistrationForm();
        login(username,password);
        verifyLoggedInUser(username);
        SessionVariables.setUsername(username);
        SessionVariables.setPassword(password);
        System.out.println(username+" "+password);
    }
}
