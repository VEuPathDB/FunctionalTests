package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.WebDriver;

public abstract class AjaxPage extends Page {

  public AjaxPage(WebDriver driver, String url) {
    super(driver, url);
  }

  public abstract void waitForPageToLoad();
  
}
