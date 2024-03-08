package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Service extends Page {
	
  private By content = By.cssSelector("body > pre");
  
  public Service(WebDriver driver) {
    super(driver, null);
  }
  
  public String pageContent() {
	//System.err.println("Here is the line");
	//System.err.println(driver.findElement(content).getText());
    return driver.findElement(content).getText();
  }
  
}
