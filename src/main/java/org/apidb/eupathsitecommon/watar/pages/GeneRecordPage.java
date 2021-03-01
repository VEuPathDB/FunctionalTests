package org.apidb.eupathsitecommon.watar.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

public class GeneRecordPage extends AjaxPage {
  
  private By recordOverview = By.cssSelector(".eupathdb-RecordOverview");
  private By recordMain = By.cssSelector(".wdk-RecordMain");
  private By categoryTree = By.cssSelector(".wdk-CheckboxTree");
  
  public GeneRecordPage(WebDriver driver, String url){
    super(driver, url);
  }

  @Override
  public void waitForPageToLoad() {
    new WebDriverWait(driver, 30, 3)
    .until(ExpectedConditions.and(
        ExpectedConditions.presenceOfElementLocated(recordOverview),
        ExpectedConditions.presenceOfElementLocated(recordMain),
        ExpectedConditions.presenceOfElementLocated(categoryTree)
        ));
  }



  
}