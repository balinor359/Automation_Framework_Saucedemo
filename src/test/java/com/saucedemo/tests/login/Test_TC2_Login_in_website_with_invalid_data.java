package com.saucedemo.tests.login;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.TestUtilities;
import org.testng.annotations.Test;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.UserBuilder;

public class Test_TC2_Login_in_website_with_invalid_data extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, validate error messages, after that validation method from the Homepage object
     */

    @Test
    public void loginWithInvalidDataInput() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC2: Login in website with invalid data");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(4));
        loginPage.validateErrorMsgWrongUsernameAndPassword();
        homePage.homepageValidator();

    }
}
