package com.saucedemo.tests.common;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC12_Verify_add_to_cart_buttons_work_correctly_on_problem_user extends TestUtilities {
    @Test
    public void verifyAllAddToCartButtonsProblemUser() {
        MyFileWriter.writeToLog("TC12: Verify add-to-cart buttons work correctly on (problem_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(2));
//        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        //todo С нормален user get(0) , всичко е ок и теста минава
        homePage.homepageValidator();

        homePage.addAllItemToTheCart();

        Assert.assertEquals(homePage.getItemsInTheCart(),6, HomePage.CART_BADGE_WRONG_AMOUNT);
        //todo Грешно ли е че assert-a е след добавянето и валидацията, след като в самия тест кейс са в различна подредба?
        //     1.1 Page is loaded
        //     1.2 Login form is shown
        //     2. Username successfully entered in field
        //     3. Password successfully entered in field
        //     4.1 User is navigated to Home page
        //     4.2 Product list are shown
        //     4.3 Menu is shown
        //     4.4 Cart is shown
        //     5.1 Red shopping cart badge with product amount "6" is shown
        //     5.2 Clicked "Add to cart" buttons become with: red border / red font color / with text "Remove"


    }
}
