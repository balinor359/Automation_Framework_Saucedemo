package com.saucedemo.tests.common;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TC12_Verify_add_to_cart_buttons_work_correctly_on_problem_user extends TestUtilities {
    @Test
    public void verifyAllAddToCartButtonsProblemUser() {
        MyFileWriter.writeToLog("TC12: Verify add-to-cart buttons work correctly on (problem_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(2));
        homePage.homepageValidator();

//        SoftAssert softAssert = new SoftAssert();//todo for delete

        homePage.addAllItemToTheCart();

        Assert.assertEquals(homePage.getItemsInTheCart(),6, HomePage.CART_BADGE_WRONG_AMOUNT);

//        homePage.selectProduct("Sauce Labs Bolt T-Shirt");

//        homePage.selectProduct("Sauce Labs Fleece Jacket");
        //todo в този случей какво се прави, след като има различен елемент и waitClicable спира целия тест?
        //така ли се оставя теста? завършен ли е ? той така или иначе ще гърми, или трябва да се вкара в POM файла елемента който по принцип не му е там мястото
        //в случея name="add-to-cart-item-not-found", и да се направи специално проверка за него за да го сравним с нормалния add to cart?

//        ProductPage productPage = homePage.openProductPage();
//        productPage.productPageValidator();
//        homePage.menuLinksValidator();
//        homePage.clickMenuInventoryButton();

//        softAssert.assertAll();//todo for delete

//        simpleWait(2000);           //todo Ако я няма тази пауза излиза error-a
        //org.openqa.selenium.remote.http.WebSocket$Listener onError WARNING: Connection reset
//        homePage.homepageValidator();

    }
}
