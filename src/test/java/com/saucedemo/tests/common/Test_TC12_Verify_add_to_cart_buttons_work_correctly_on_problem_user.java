package com.saucedemo.tests.common;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC12_Verify_add_to_cart_buttons_work_correctly_on_problem_user extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Add all products to the cart from home page, and check is the cart amount changed.
     */

    @Test
    public void verifyAllAddToCartButtonsProblemUser() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC12: Verify add-to-cart buttons work correctly on (problem_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(2));
        homePage.homepageValidator();

        homePage.addAllItemToTheCart();

        Assert.assertEquals(homePage.getItemsInTheCart(), 6, HomePage.CART_BADGE_WRONG_AMOUNT);


    }
}
