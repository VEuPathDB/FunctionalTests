package org.apidb.eupathsitecommon.watar.pages;

import org.apidb.eupathsitecommon.watar.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class GenesByTaxonSearchPage extends SearchForm{

  private By firstOrganismCheckbox = By.cssSelector(".wdk-CheckboxTreeItem:nth-child(1) .wdk-CheckboxTreeCheckbox");

  
  
  public GenesByTaxonSearchPage(WebDriver driver, String baseurl) {
    super(driver, true, baseurl + Utilities.GENES_BY_TAXON_SEARCH);
  }
  
  public void clickFirstTaxon() {
    this.driver.findElement(this.firstOrganismCheckbox).click();
  }

  
  

}
