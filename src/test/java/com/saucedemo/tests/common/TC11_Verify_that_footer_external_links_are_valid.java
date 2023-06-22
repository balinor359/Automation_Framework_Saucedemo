package com.saucedemo.tests.common;

import com.saucedemo.pom.HomePage;
import org.testng.annotations.Test;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;

public class TC11_Verify_that_footer_external_links_are_valid extends TestUtilities{

    @Test
    public void clickFooterSocialLinks(){
        MyFileWriter.writeToLog("TC11: Verify_that_footer_external_links_are_valid");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.socialLinksValidator();


    }
//    @Test
//    public void addItemToTheCart(){
//        MyFileWriter.writeToLog("TC11: addItemToTheCart");
//        UserBuilder.createUserList();
//
//        LoginPage loginPage = new LoginPage(TestUtilities.driver);
//        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
//        homePage.homepageValidator();
//        homePage.addItemToTheCart("fleece-jacket");
////        homePage.addItemToTheCart("onesie");
//
//        //Hard Assert
//        Assert.assertEquals(homePage.getItemsInTheCart(),1, "Because we have only one item so far");
//
//
//        homePage.removeItemFromTheCart("fleece-jacket");
////        homePage.removeItemFromTheCart("onesie");
//        //Hard Assert
//        Assert.assertEquals(homePage.getItemsInTheCart(),0, "Because we have only one item so far");
//
//
//        //Soft Assert
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertEquals(homePage.getItemsInTheCart(), 1 ,"Because we have only one item so far");
//        System.out.println("I will be executed");
//
//        //for example at the end of the test
//        softAssert.assertAll(); // now all asserts
//    }
}
