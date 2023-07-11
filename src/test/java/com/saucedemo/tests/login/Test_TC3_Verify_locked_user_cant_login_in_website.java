package com.saucedemo.tests.login;

import com.saucedemo.pom.HomePage;
import org.testng.annotations.Test;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;

public class Test_TC3_Verify_locked_user_cant_login_in_website extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, validate error messages, after that validation method from the Homepage object
     */

    @Test
    public void loginWithLockedUser() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC3: Verify locked user cant login in website");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(1));
        loginPage.validateErrorMsgInvalidUser();
        homePage.homepageValidator();

    }
}
