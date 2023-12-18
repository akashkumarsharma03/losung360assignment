package org.automation.PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleSearchPage {
    WebDriver driver;
    By searchtextbox = By.name("q");
    By totalsearch = By.cssSelector(".tF2Cxc a");

    public GoogleSearchPage(WebDriver driver){
        this.driver = driver;
    }
    public void washingMachineSearch(String value){
       WebElement element =  driver.findElement(searchtextbox);
       element.sendKeys(value);
       element.submit();
    }
    public List totalSearch(){

        return driver.findElements(totalsearch);
    }
}


