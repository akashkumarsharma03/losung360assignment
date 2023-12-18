package org.automation;

import base.baseclass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.automation.PageObjectModel.GoogleSearchPage;
import org.automation.PageObjectModel.SearchResultPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static base.baseclass.takeScreenshot;


public class GoogleSearchAutomation {
    private WebDriver driver;
    Map<String, String> url = new HashMap<>();

    @Given("I open Google search page")
    public void openGoogleSearchPage() {
        baseclass.setUp();
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
    }

    @When("^I search for \"(.*)\"$")
    public void searchFor(String searchvalue)throws Throwable {
        GoogleSearchPage search = new GoogleSearchPage(driver);
        search.washingMachineSearch(searchvalue);

    }

    @Then("I validate the number of Amazon and Flipkart links on the first page")
    public void validateAmazonFlipkartLinks() throws InterruptedException {
        GoogleSearchPage searchResult = new GoogleSearchPage(driver);
        baseclass.windowscroll(driver);
        Thread.sleep(10000);
        List<WebElement> searchList = searchResult.totalSearch();
        int amazonLinks = 0;
        int flipkartLinks = 0;

        try {
            for (WebElement result : searchList) {
                String links = result.getAttribute("href");
                System.out.println(links);
                baseclass.logging(links);
                System.out.println("links size"+result.getSize());
                if (links != null && links.contains("flipkart")) {
                    flipkartLinks++;
                    url.put("flipkart"+flipkartLinks,links);
                } else if (links != null && links.contains("amazon")) {
                    amazonLinks++;
                    url.put("amazon"+amazonLinks,links);
                }

            }
        } catch (Exception e) {
            baseclass.warning(String.valueOf(e));

        } finally {
            baseclass.logging("Amazon Links: " + amazonLinks);
           baseclass.logging("Flipkart Links: " + flipkartLinks);
        }
    }


    @Then("I navigate to each Amazon link and confirm the title and results count")
    public void navigateToAmazonLinks() {
       SearchResultPage amazonsearchpage = new SearchResultPage(driver);
       int a= 0;
       int b =0;
        for (Map.Entry<String, String> entry : url.entrySet()) {
            if (entry.getKey().contains("amazon")) {
                driver.get(entry.getValue());
                String title = driver.getTitle();
                baseclass.logging(title);
                takeScreenshot(driver, "screenshots", "amazonpage"+b++);
                String products = amazonsearchpage.amazon();
                baseclass.logging(products);
                if(products==null){
                    System.out.println("Washing machine are zero at amazon");
                    baseclass.logging("Washing machine zero at amazon");
                }else {
                    for (int i = 0; i < products.length(); i++) {
                        char ch = products.charAt(i);
                        a = Character.getNumericValue(ch);
                    }
                    if (a > 0) {
                        System.out.println("Washing machine are greater than zero at amazon");
                        baseclass.logging("Washing machine are greater than zero at amazon");
                    }
                }
            }

        }

    }

    @Then("I navigate to each Flipkart link and confirm the title and results count")
    public void navigateToFlipkartLinks() {
        SearchResultPage flipkartsearchpage = new SearchResultPage(driver);
        int a=0;
        int b=0;
        for (Map.Entry<String, String> entry : url.entrySet()) {
            if (entry.getKey().contains("flipkart")) {
                driver.get(entry.getValue());
                String title = driver.getTitle();
                baseclass.logging(title);
                takeScreenshot(driver, "screenshots", "flipkartpage"+b++);
                String products = flipkartsearchpage.flipkart();
                baseclass.logging(products);
                if(products==null){
                    System.out.println("Washing machine are zero at amazon");
                    baseclass.logging("Washing machine zero at amazon");
                }else {
                    for (int i = 0; i < products.length(); i++) {
                        char ch = products.charAt(i);
                        a = Character.getNumericValue(ch);
                    }
                    if (a > 0) {
                        System.out.println("Washing machine are greater than zero at flipkart");
                    }
                }
            }

        }
        baseclass.tearDown();
    }

    @And("I close the browser")
    public void iCloseTheBrowser() {
        driver.close();
        driver.quit();
    }
}
