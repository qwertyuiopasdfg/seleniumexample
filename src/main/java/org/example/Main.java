package org.example;

import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.InterruptedException;
public class Main {
    public static void main(String[] args) throws InterruptedException {

        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(opt);
        driver.get("https://nseindia.com");


        WebElement mButton = driver.findElement(By.id("link_2"));
        Actions hover = new Actions(driver);
        hover.moveToElement(mButton).perform();
        Thread.sleep(3000);


        WebElement pButton = driver.findElement(By.linkText("Pre-Open Market"));
        pButton.click();

        Thread.sleep(6000);

        WebElement table = driver.findElement(By.id("livePreTable"));
        //*[@id="livePreTable"]

        List<WebElement> str = table.findElements(By.xpath(".//tr"));
        str.remove(0);
        str.remove(str.size()-1);

        List<String[]> rows = new ArrayList<>();
        rows.add (new String[] {"NAME","PRICE"});
        for (WebElement row : str) {
            String[] stroki = row.getText().split(" ");
            String[] vivod = {stroki[0], stroki[5]};
            rows.add(vivod);
        }

        String path = "final.csv";
        try (FileWriter fw = new FileWriter(path); CSVWriter csvw = new CSVWriter(fw, ';', CSVWriter.NO_QUOTE_CHARACTER)) {
            csvw.writeAll(rows);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        driver.navigate().back();

        Thread.sleep(3000);
        new Actions(driver).scrollByAmount(0, 700).perform();
        Thread.sleep(2000);
        WebElement view = driver.findElement(By.id("viewall"));

        view.click();
        Thread.sleep(2000);
        WebElement dropd = driver.findElement(By.id("equitieStockSelect"));
        //dropd.click();
        Select select = new Select(dropd);
        select.selectByValue("NIFTY ALPHA 50");
        Thread.sleep(2000);
        WebElement scroll = driver.findElement(By.xpath("//*[@id=\"marketWatchEquityCmsNote\"]"));
        new Actions(driver).scrollToElement(scroll).perform();
    }
}