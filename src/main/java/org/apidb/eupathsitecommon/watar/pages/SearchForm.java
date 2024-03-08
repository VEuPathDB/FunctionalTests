package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchForm extends AjaxPage {
 
  private By standardQuestionFormParams = By.cssSelector(".wdk-QuestionFormParameterList");
  private By foldChangeParams = By.cssSelector(".wdk-FoldChangeParams");
  private By wizardParams = By.cssSelector(".ebrc-QuestionWizardParamContainer");
  
  private By cookiesBanner = By.className("wdk-Link");

  private By getAnswer = By.xpath("//button[contains(.,'Get Answer')]");
  
  private boolean hasParameters;
    
  public SearchForm(WebDriver driver, boolean hasParameters, String queryPage){
    super(driver, queryPage);
    this.hasParameters = hasParameters;
  }

  public void getAnswer() {
    this.driver.findElement(this.getAnswer).click();
  }

  public void closeBanner() {
    this.driver.findElement(this.cookiesBanner).click();
  }

  public boolean containsError() {
    if(this.driver.getPageSource().contains("Unknown parameter")) {
      return true;
    }
    return false;
  }

  @Override
  public void waitForPageToLoad() {
    if(hasParameters) {
      new WebDriverWait(driver, 30, 3)
      .until(ExpectedConditions.or(
          ExpectedConditions.presenceOfElementLocated(standardQuestionFormParams),
          ExpectedConditions.presenceOfElementLocated(foldChangeParams),
          ExpectedConditions.presenceOfElementLocated(wizardParams)
          ));
    }   
   }
   
}
