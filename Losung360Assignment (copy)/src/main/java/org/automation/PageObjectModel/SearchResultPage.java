package org.automation.PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage {
    WebDriver driver;

    public SearchResultPage(WebDriver driver){
        this.driver = driver;
    }

    By amazonResult = By.xpath("//*[@id=\"search\"]/span[2]/div/h1/div/div[1]/div/div/span[1]");
    By flipkartresult = By.className("_2tDckM");

    public String amazon() {
        String search;
        try {
            search = driver.findElement(amazonResult).getText();
        }catch (Exception e){
            System.out.println(e);
            search = null;
        }
        return search;
    }
    public String flipkart(){
        String search;
        try {
            search = driver.findElement(flipkartresult).getText();
        }
        catch(Exception e){
            System.out.println(e);
            search = null;
        }
        return search;
    }
    }

