package com.saucedemo.tests.login;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.TestUtilities;
import org.testng.annotations.Test;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.UserBuilder;

public class TC2_Login_in_website_with_invalid_data extends TestUtilities {
    @Test
    public void loginWithInvalidDataInput(){
        MyFileWriter.writeToLog("TC2: Login_in_website_with_invalid_data");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(4));
        loginPage.checkErrorMsgWrongUsernameAndPassword();
        homePage.homepageValidator();

    }
}
