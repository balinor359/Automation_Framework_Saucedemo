package com.saucedemo.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.atomic.AtomicInteger;

public class WebDriverFactory {
    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    private static AtomicInteger activeInstanceCount = new AtomicInteger(0);
    public static WebDriver getWebDriver(){
        return webDriverThreadLocal.get();
    }
    public static void createWebDriver(){
        WebDriver driver = new ChromeDriver();
        webDriverThreadLocal.set(driver);
        activeInstanceCount.incrementAndGet();
    }
    public static void closeWebDriver(){
        WebDriver driver = webDriverThreadLocal.get();
        if (driver != null){
            driver.quit();
            webDriverThreadLocal.remove();
            activeInstanceCount.decrementAndGet();
        }
    }
    public static int getActiveInstanceCount(){
        return activeInstanceCount.get();
    }
}
