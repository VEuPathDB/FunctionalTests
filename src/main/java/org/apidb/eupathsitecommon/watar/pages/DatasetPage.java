package org.apidb.eupathsitecommon.watar.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

public class DatasetPage extends AjaxPage {
  
  private By references = By.cssSelector("#References .wdk-CollapsibleSectionHeader");
  private By recordMain = By.cssSelector(".wdk-RecordMain");
  
  private By unhandledErrors = By.cssSelector(".UnhandledErrors");
  
  public DatasetPage(WebDriver driver){
    super(driver);
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
  }

  @Override
  public void waitForPageToLoad() {
    new WebDriverWait(driver, 30, 3)
    .until(ExpectedConditions.and(
        ExpectedConditions.presenceOfElementLocated(references),
        ExpectedConditions.presenceOfElementLocated(recordMain)
        ));
  }

  
  public boolean containsError() {
    try {
      if(driver.findElements(unhandledErrors).size() > 0) {
        return true;
      }
    } catch (org.openqa.selenium.NoSuchElementException e) {
      return false;
    }  
    return false;

  }


  
}
