package org.apidb.eupathsitecommon.watar.pages;

import org.apidb.eupathsitecommon.watar.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class GenesByTaxonSearchPage extends SearchForm{

  private By firstOrganismCheckbox = By.cssSelector("#wdk-container > div > main > div > form > div:nth-child(1) > div > div.wdk-QuestionFormParameterControl > div > div > div:nth-child(4) > ul > li:nth-child(1) > div > label > input[type=checkbox]");

  
  
  public GenesByTaxonSearchPage(WebDriver driver, String baseurl) {
    super(driver, true, baseurl + Utilities.GENES_BY_TAXON_SEARCH);
  }
  
  public void clickFirstTaxon() {
    this.driver.findElement(this.firstOrganismCheckbox).click();
  }

  
  

}
