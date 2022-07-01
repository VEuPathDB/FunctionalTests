package org.apidb.eupathsitecommon.watar.pages;

import org.apidb.eupathsitecommon.watar.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

public class SequenceRetrievalTool extends AjaxPage {
  //This no longer finds anything, and causes the test to break. Not sure what this was looking for, so I can not redirect it.
  //private By srtMainSection = By.cssSelector(".wdk-TabContent");
  private By submitButton = By.xpath("//button[contains(.,'Get Sequences')]");

  public SequenceRetrievalTool(WebDriver driver, String baseUrl){
    super(driver, baseUrl + Utilities.SRT_TOOL);
  }

  @Override
  public void waitForPageToLoad() {
    new WebDriverWait(driver, 30, 3)
    .until(ExpectedConditions.and(
        //ExpectedConditions.presenceOfElementLocated(this.srtMainSection),
        ExpectedConditions.presenceOfElementLocated(this.submitButton)
        ));
  }
  
  public void submit() {
      WebElement submitButton = this.driver.findElement(this.submitButton);
      JavascriptExecutor js = (JavascriptExecutor) this.driver;
      js.executeScript("arguments[0].scrollIntoView();", submitButton);
      
      submitButton.click();
  }
  
}

