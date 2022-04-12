package org.apidb.eupathsitecommon.watar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apidb.eupathsitecommon.watar.Utilities;

public class UserComments extends AjaxPage{

    private By getAnswer = By.xpath("//button[contains(.,'Get Answer')]");
    private By stepBoxesStepCount = By.className("StepBoxes--StepCount");
  
    public UserComments(WebDriver driver, String baseurl) {
	super(driver, baseurl);
    }
  
  @Override
  public void waitForPageToLoad() {
      new WebDriverWait(driver, 30, 3)
	  .until(ExpectedConditions.presenceOfElementLocated(getAnswer));
  }

    public int getCommentCount(String baseurl) {
	driver.get(baseurl + Utilities.GENES_WITH_USER_COMMENTS);
	driver.findElement(getAnswer).click();
	String commentCount = driver.findElement(stepBoxesStepCount).getText();
	String noGenes = commentCount.replace(" Genes", "");
	String noCommas = noGenes.replace(",", "");
	int commentCountFinal = Integer.parseInt(noCommas);
	return commentCountFinal;
    }


}
