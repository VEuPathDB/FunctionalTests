package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends AjaxPage{
  
  private By checkBoxTreeItem = By.cssSelector("#wdk-container > div > nav > div > div > div > div.css-1l4w6pd > div > label > input[type=search]");

  private By selectedToolBody = By.cssSelector(".vpdb-FeaturedToolsSelectionBody");

  private By selectedTool = By.cssSelector(".vpdb-FeaturedToolsSelectionHeader");

  private By anotherTool = By.cssSelector(".fa-code-fork");
  
  private By siteSearchBox = By.name("q");
  private By siteSearchSubmit = By.cssSelector("button > .fa-search");
    
  public HomePage(WebDriver driver, String baseurl){
    super(driver, baseurl);
  }

  public void changeSelectedTool() {
    changeSelectedTool(anotherTool);
  }

  public void changeSelectedTool(By tool) {
    driver.findElement(tool).click();
  }
  
  public int selectedToolBodyCount() {
    return driver.findElements(selectedToolBody).size();
  }

  
  @Override
  public void waitForPageToLoad() {
    new WebDriverWait(driver, 30, 3)
    .until(ExpectedConditions.presenceOfElementLocated(checkBoxTreeItem));
  }
  
  public String selectedToolHeaderText() {
    return driver.findElement(selectedTool).getText();
  }
  
  public void doSiteSearch(String text) {
    this.driver.findElement(this.siteSearchBox).sendKeys(text);
    this.driver.findElement(this.siteSearchSubmit).click();
  }
  
  
}
