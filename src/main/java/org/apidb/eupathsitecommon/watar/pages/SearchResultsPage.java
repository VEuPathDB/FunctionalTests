package org.apidb.eupathsitecommon.watar.pages;

import java.text.NumberFormat;
import java.text.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage extends AjaxPage{

  private By primaryKeyColumn = By.cssSelector(".HeadingCell--key-primary_key");
  private By organismFilterFirstNode = By.cssSelector("#wdk-container > div > main > div > div:nth-child(3) > div:nth-child(2) > div.OrganismFilter > div:nth-child(1) > div:nth-child(5) > ul > li:nth-child(1) > div > label > div > div > div:nth-child(2)");
  
  public SearchResultsPage(WebDriver driver, String url) {
    super(driver, url);
  }

  @Override
  public void waitForPageToLoad() {
    new WebDriverWait(driver, 30, 3)
    .until(
        ExpectedConditions.presenceOfElementLocated(this.primaryKeyColumn)
        );
  }

  public int organismFilterFirstNodeCount() {
    new WebDriverWait(driver, 30, 3)
    .until(
        ExpectedConditions.presenceOfElementLocated(this.organismFilterFirstNode)
        );
    try {
      return NumberFormat.getNumberInstance(java.util.Locale.US).parse(this.driver.findElement(this.organismFilterFirstNode).getText()).intValue();
      // return Integer.parseInt(this.driver.findElement(this.organismFilterFirstNode).getText());
    }
    catch (ParseException e) {
    }
    return 0;
  }


}
