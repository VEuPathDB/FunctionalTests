package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class GenesByTaxonSearchPage extends SearchForm{

  private By firstOrganismCheckbox = By.cssSelector(".wdk-CheckboxTreeItem:nth-child(1) .wdk-CheckboxTreeCheckbox");

  
  
  public GenesByTaxonSearchPage(WebDriver driver, boolean hasParameters) {
    super(driver, hasParameters);
  }
  
  public void clickFirstTaxon() {
    this.driver.findElement(this.firstOrganismCheckbox).click();
  }

  
  

}
