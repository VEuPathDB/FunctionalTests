package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

public class SiteSearchResults extends AjaxPage{
  
  private By siteSearchCounts = By.cssSelector(".SiteSearch--CountsContainer");
  private By siteSearchResults = By.cssSelector(".SiteSearch--Results");
  
  private By siteSearchExactMatch = By.cssSelector(".SiteSearch--ResultLink__exact span");

  public SiteSearchResults(WebDriver driver){
    super(driver, null);
  }

  @Override
  public void waitForPageToLoad() {
    new WebDriverWait(driver, 30, 3)
    .until(ExpectedConditions.and(
        ExpectedConditions.presenceOfElementLocated(siteSearchCounts),
        ExpectedConditions.presenceOfElementLocated(siteSearchResults)
        ));
  }

  public String findExactMatchString() {
    return this.driver.findElement(siteSearchExactMatch).getText();
  }
  
  
}
