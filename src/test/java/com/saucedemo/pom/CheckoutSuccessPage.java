package com.saucedemo.pom;

import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CheckoutSuccessPage extends TestUtilities {
    protected WebDriver driver;
    public CheckoutSuccessPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
