package utilities;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class SeleniumUtils {

    /**
     * This method clicks on the given element, switches to the newly opened tab
     * and prints its url.
     * @param driver - is used to open web application
     * @param element - is clicked to open new tab
     */
    public static void switchToNewTab(WebDriver driver, WebElement element){
        String mainWindow = driver.getWindowHandle();

        element.click();

        for (String windowHandle : driver.getWindowHandles()){
            if (!windowHandle.equals(mainWindow)){
                driver.switchTo().window(windowHandle);
            }
        }

        System.out.println("Currently, the driver is on: " + driver.getCurrentUrl());
    }

    public static void waitForSeconds(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e){

        }
    }

    /**
     * This method waits for element to be clickable
     * before clicking on it
     * @param driver - is used to open web application
     * @param element - to be clicked
     */
    public static void click(WebDriver driver, WebElement element){
        FluentWait wait = new FluentWait(driver)
                .ignoring(ElementClickInterceptedException.class)
                .withTimeout(Duration.ofSeconds(30));

        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.click();
    }

}
