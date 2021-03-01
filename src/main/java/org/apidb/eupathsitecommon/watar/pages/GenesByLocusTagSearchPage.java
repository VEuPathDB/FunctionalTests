package org.apidb.eupathsitecommon.watar.pages;

import org.apidb.eupathsitecommon.watar.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GenesByLocusTagSearchPage extends SearchForm {
  
  private By textArea = By.cssSelector("textarea");
  
  public GenesByLocusTagSearchPage(WebDriver driver, String baseurl) {
    super(driver, true, baseurl + Utilities.GENES_BY_LOCUS_SEARCH);
  }
  
  public String scrapeGeneIdFromTextArea() {
    return this.driver.findElement(this.textArea).getAttribute("value");
  }
  

}
