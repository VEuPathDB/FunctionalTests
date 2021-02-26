package org.apidb.eupathsitecommon.watar.pages;

import java.text.NumberFormat;
import java.text.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage extends AjaxPage{

  private By primaryKeyColumn = By.cssSelector(".HeadingCell--key-primary_key");
  private By organismFilterFirstNode = By.cssSelector(".wdk-CheckboxTreeItem:nth-child(1) .OrganismFilter--NodeCount");
  
  public SearchResultsPage(WebDriver driver) {
    super(driver);
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
