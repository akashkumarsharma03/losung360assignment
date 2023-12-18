package base;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Logger;

import java.util.Set;

public class baseclass {
    public WebDriver driver;

    public baseclass(WebDriver driver){
        this.driver = driver;
    }
    public static Logger log = (Logger) LogManager.getLogger();


    public static void setUp() {
            log.info("Test Start");
    }

    public static void logging(String info){
        log.info(info);
    }

    public static void warning(String info){
        log.warn(info);
    }


    public static void tearDown() {
        log.info("Test Stop");
    }

    public static void  windowscroll(WebDriver driver){
        int prevHeight = -1;
        int maxScrolls = 100;
        int scrollCount = 0;

        while (scrollCount < maxScrolls) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(1000); // Give some time for new results to load
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int newHeight = Integer.parseInt(((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());

            if (newHeight == prevHeight) {
                break;
            }

            prevHeight = newHeight;
            scrollCount++;
        }

    }

    public static void takeScreenshot(WebDriver driver, String folderPath, String fileName) {
        //checkAndEmptyFolder(folderPath);
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = folderPath + File.separator + fileName + ".png";
        try {
            Files.move(screenshotFile.toPath(), Paths.get(screenshotPath));
            System.out.println("Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void checkAndEmptyFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory() && folder.list().length > 0) {
            System.out.println("Emptying the screenshot folder: " + folderPath);
            emptyFolder(folder);
        }
    }
    private static void emptyFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    emptyFolder(file);
                }
                file.delete();
            }
        }
    }
}

