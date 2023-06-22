package com.saucedemo.tests.login;

import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;
import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.TestUtilities;

public class TC1_Login_in_website_with_valid_data extends TestUtilities {

    //todo  IN THE ENT COMMENTS!
    //The test uses the generated users from CSV file, initializes the web driver using the LoginPage object,
    // then calls the login method, then the validation method from the Homepage object

    @Test
    public void loginWithValidDataInput() {
        MyFileWriter.writeToLog("TC1: Login_in_website_with_valid_data");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

//        System.out.println(UserBuilder.fullUsersList);
//        System.out.println(UserBuilder.fullUsersList.get(0));
//        System.out.println(UserBuilder.fullUsersList.get(1));
//        System.out.println(UserBuilder.fullUsersList.get(2));
//        System.out.println(UserBuilder.fullUsersList.get(3));
//        System.out.println(UserBuilder.fullUsersList.get(4));

    }
}
