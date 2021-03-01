package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.WebDriver;

public abstract class Page {

  protected WebDriver driver;

  public Page(WebDriver driver, String url){
    this.driver = driver;
    if(url != null && !url.isEmpty()) {
      driver.get(url);
    }
    for(String winHandle : driver.getWindowHandles()){
      driver.switchTo().window(winHandle);
    }
  }

}
