package org.apidb.eupathsitecommon.watar;

//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;     
import org.openqa.selenium.Dimension;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.json.JSONArray;
import org.json.JSONObject;

//import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.apidb.eupathsitecommon.watar.pages.DatasetPage;
import org.apidb.eupathsitecommon.watar.pages.GeneRecordPage;
import org.apidb.eupathsitecommon.watar.pages.GenesByLocusTagSearchPage;
import org.apidb.eupathsitecommon.watar.pages.GenesByTaxonSearchPage;
import org.apidb.eupathsitecommon.watar.pages.HomePage;
import org.apidb.eupathsitecommon.watar.pages.LoginPage;
import org.apidb.eupathsitecommon.watar.pages.SearchForm;
import org.apidb.eupathsitecommon.watar.pages.SearchResultsPage;
import org.apidb.eupathsitecommon.watar.pages.Service;
import org.apidb.eupathsitecommon.watar.pages.SiteSearchResults;
import org.apidb.eupathsitecommon.watar.pages.StaticContent;

public class SeleniumTests {
  private WebDriver driver;
  //private JavascriptExecutor js;

  private String baseurl;
  private String username;
  private String password;
  
//  private boolean isPortal;

  public SeleniumTests() {

    baseurl = System.getProperty("baseurl") + "/" + System.getProperty("webappname");
    username = System.getProperty("username");
    password = System.getProperty("password");

    //isPortal = baseurl.toLowerCase().contains("eupathdb");

  }

  @BeforeTest
  public void setUp() {
   this.driver = new ChromeDriver();
   driver.manage().window().setSize(new Dimension(1000, 1000));
   
   LoginPage loginPage = new LoginPage(driver, username, password);
   loginPage.login();

   
   //js = (JavascriptExecutor) driver;
  }
  @AfterTest
  public void tearDown() {
   driver.quit();
  }

  
  @DataProvider(name = "staticPages")
  public Iterator<Object[]> createStaticPages() {
    ArrayList<Object[]> staticPages = new ArrayList<Object[]>();

    Object[] ds = { Utilities.DATA_SUBMISSION, "Data Submission" }; 
    Object[] news = { Utilities.NEWS_PATH, "News" }; 
    Object[] pubs = { Utilities.PUBLICATIONS, "Publications" }; 
    Object[] rel_sites = { Utilities.RELATED_SITES, "Related Sites" }; 
    Object[] about = { Utilities.ABOUT, "About"}; 
    Object[] privacy = { Utilities.PRIVACY_POLICY, "Privacy Policy"}; 
    Object[] personnel = { Utilities.PERSONNEL, "Personnel"}; 
    Object[] acks = { Utilities.ACKS, "Acknowledgements"}; 
    Object[] methods = { Utilities.METHODS, "Methods"}; 
    Object[] infra = { Utilities.INFRASTRUCTURE, "Infrastructure"}; 

    staticPages.add(ds);
    staticPages.add(news);
    staticPages.add(pubs);
    staticPages.add(rel_sites);
    staticPages.add(about);
    staticPages.add(privacy);
    staticPages.add(personnel);
    staticPages.add(acks);
    staticPages.add(methods);
    staticPages.add(infra);
    
    return staticPages.iterator();
  }
  
  @DataProvider(name = "searches")
  public Iterator<Object[]> createSearches() {
   
    ArrayList<Object[]> searchesArrayList = new ArrayList<Object[]>();
    
    JSONObject recordTypesJson = parseEndpoint(baseurl + "/service/record-types", "record-types");
    JSONArray recordTypesArray = (JSONArray) recordTypesJson.get("record-types");

    for(int i = 0; i < recordTypesArray.length(); i++) {
      String recordType = recordTypesArray.getString(i);

      JSONObject searches = parseEndpoint(baseurl + "/service/record-types/" + recordType + "/searches", "searches");
      JSONArray searchesArray = (JSONArray) searches.get("searches");

      for(int j = 0; j < searchesArray.length(); j++) {
        JSONObject search = (JSONObject) searchesArray.get(j);
        String urlSegment = (String) search.get("urlSegment");
        String fullName = (String) search.get("fullName");
        JSONArray paramNames = (JSONArray) search.get("paramNames");

        if(urlSegment.equals("GenesByUserDatasetAntisense") || 
            urlSegment.equals("GenesByRNASeqUserDataset") || 
            urlSegment.equals("Gendescription=\"Assert home page loads and the featured tool section is there.\",esByeQTL") ||
            urlSegment.equals("GenesFromTranscripts") ||
            urlSegment.equals("GenesByPlasmoDbDataset") ||
            urlSegment.contains("boolean_question")
            )
          continue;

        boolean hasParameters = paramNames.length() > 0;
        String queryPage = this.baseurl + "/app/search/" + recordType + "/" + urlSegment;

        Object[] sa = new Object[3];
        sa[0] = queryPage;
        sa[1] = fullName;
        sa[2] = hasParameters;

        searchesArrayList.add(sa);
      }

    }
    return searchesArrayList.iterator();
  }
  
  
  @DataProvider(name = "geneIds")
  public Iterator<Object[]> createGeneIds() {
    ArrayList<Object[]> genesArrayList = new ArrayList<Object[]>();
    GenesByLocusTagSearchPage searchForm = new GenesByLocusTagSearchPage(driver, this.baseurl);
    searchForm.waitForPageToLoad();
    String defaultGeneId = searchForm.scrapeGeneIdFromTextArea();
    Object[] ga = new Object[1];
    ga[0] = defaultGeneId;
    genesArrayList.add(ga);

  return genesArrayList.iterator();

    
  }
  
  @DataProvider(name = "datasets")
  public Iterator<Object[]> createDatasets() {
   
    ArrayList<Object[]> datasetsArrayList = new ArrayList<Object[]>();
    
    JSONObject datasetsJson = parseEndpoint(baseurl + "/service/record-types/dataset/searches/AllDatasets/reports/standard?reportConfig=%7B\"attributes\"%3A%5B\"primary_key\"%5D%2C\"tables\"%3A%5B%5D%7D", "datasets");
    JSONObject datasetsObj = (JSONObject) datasetsJson.get("datasets");
    JSONArray recordsArray = (JSONArray) datasetsObj.get("records");

    for(int i = 0; i < recordsArray.length(); i++) {
      JSONObject record = (JSONObject) recordsArray.get(i);
      JSONArray idArray = (JSONArray) record.get("id");

      JSONObject id = (JSONObject) idArray.get(0);
      
      String datasetId = id.getString("value"); 
      
      String datasetPage = this.baseurl + "/app/record/dataset/" + datasetId;

      Object[] da = new Object[2];
      da[0] = datasetPage;
      da[1] = datasetId;
      
      datasetsArrayList.add(da);
    }

    return datasetsArrayList.iterator();
  }

  
  
  
  @Test(dataProvider = "searches",
      description="Assert search page loads without error",
      groups = {"functional_tests"})
  public void searchPage(String queryPage, String fullName, boolean hasParameters) {


    SearchForm searchForm = new SearchForm(driver, hasParameters, queryPage);
    searchForm.waitForPageToLoad();

    assertTrue(!searchForm.containsError(), "Search form Contained Error: " + fullName);
  }
  @Test(description="Assert home page loads and the featured tool section is present.",
      groups = { "functional_tests", "performance_tests" })
  public void homePage () {

    long startTime = System.nanoTime();    
    HomePage homePage = new HomePage(driver, this.baseurl);
    homePage.waitForPageToLoad();

    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);

    assertTrue(homePage.selectedToolBodyCount() == 1, "assert Selected Tool Body was present");
    String initialSelectedToolText = homePage.selectedToolHeaderText();
    homePage.changeSelectedTool();
    String changedSelectedToolText = homePage.selectedToolHeaderText();
    assertTrue(!initialSelectedToolText.equals(changedSelectedToolText), "assert Selected Tool was Changed");
  }

  /**
   * Assert static content page loads without error and static-content element is present
   *
   * @param url url of the page
   * @param name name of the page
   */
  @Test(dataProvider="staticPages", 
      description="Assert static content page loads without error and static-content element is present",
      groups = { "functional_tests" })
  public void staticPage (String url, String name) {
    String staticPageUrl = this.baseurl + url;
    StaticContent staticContentPage = new StaticContent(driver, staticPageUrl);
    staticContentPage.waitForPageToLoad();
  }

  @Test(dataProvider="datasets", 
      description="Assert dataset page loads without error.  Checks for cross-refs of wdkSearches",
      groups = { "functional_tests" })
  public void datasetPage (String datasetPageUrl, String datasetId) {
    DatasetPage datasetPage = new DatasetPage(driver, datasetPageUrl);
    datasetPage.waitForPageToLoad();
    assertTrue(!datasetPage.containsError(), "Failure on DatasetPage: " + datasetId);
  }

  @Test(description="Performance Test Filter Param Search Form",
      groups = { "performance_tests" })
  public void geneFilterSearchPage () {

    String url = this.baseurl + Utilities.GENE_MODEL_CHARS_SEARCH; 
    long startTime = System.nanoTime();        

    SearchForm searchForm = new SearchForm(driver, true, url);
    searchForm.waitForPageToLoad();

    assertTrue(!searchForm.containsError(), "Search form Contained Error: " + Utilities.GENE_MODEL_CHARS_SEARCH);

    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);
    
  }

  
  @Test(description="Performance Test Simple Search Result",
      groups = { "functional_tests", "performance_tests" })
  public void geneSearchResultsPage () {

    int expectedResult = 1000;
    GenesByTaxonSearchPage searchForm = new GenesByTaxonSearchPage(driver, this.baseurl);
    searchForm.waitForPageToLoad();
    assertTrue(!searchForm.containsError(), "Search form Contained Error: " + Utilities.GENES_BY_TAXON_SEARCH);

    searchForm.clickFirstTaxon();
    searchForm.getAnswer();

    long startTime = System.nanoTime();    
    // The search form click() above navigates to this page (NO url provided to constructor)
    SearchResultsPage searchResults = new SearchResultsPage(driver, null);
    searchResults.waitForPageToLoad();
    int resultSize = searchResults.organismFilterFirstNodeCount();
    assertTrue(resultSize > expectedResult, "Search Resulted in " + resultSize + " which is less than the expected size of " + expectedResult);

    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);

  }


  @Test(description="Performance Test Organism Results Page",
      groups = { "functional_tests", "performance_tests" })
  public void organismResultsPage () {

    String url = this.baseurl + Utilities.ORGANISM_RESULTS; 
    long startTime = System.nanoTime();    
    SearchResultsPage searchResults = new SearchResultsPage(driver, url);
    searchResults.waitForPageToLoad();
    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);
}

  @Test(description="Performance Test Datasets Results Page",
      groups = { "functional_tests", "performance_tests" })
  public void datasetResultsPage () {

    String url = this.baseurl + Utilities.DATASET_RESULTS; 
    long startTime = System.nanoTime();

    SearchResultsPage searchResults = new SearchResultsPage(driver, url);
    searchResults.waitForPageToLoad();
    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);    
  }

  
  @Test(dataProvider="geneIds", 
        description="Performance Test Default Gene Record Page",
        groups = { "functional_tests", "performance_tests" })
  public void geneRecordPage (String geneId) {
    String geneRecordUrl = this.baseurl + Utilities.GENE_RECORD_PATH_TMPL + geneId;

    long startTime = System.nanoTime();
    GeneRecordPage geneRecordPage = new GeneRecordPage(this.driver, geneRecordUrl);
    geneRecordPage.waitForPageToLoad();
    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);    
  }
  
  @Test(description="Performance Test Sequence Retrieval Tool",
      groups = { "functional_tests", "performance_tests" })
  public void geneSrtTool (String geneId) {

    // TODO
    // SRT object
    // SRT Result?
  
}

  @Test(dataProvider="geneIds", 
        description="Performance Test Site Search",
        groups = { "functional_tests", "performance_tests" })
  public void geneIdSiteSearch (String geneId) {


    HomePage homePage = new HomePage(driver, this.baseurl);
    homePage.waitForPageToLoad();

    long startTime = System.nanoTime();    
    homePage.doSiteSearch(geneId);
    SiteSearchResults siteSearchResults = new SiteSearchResults(this.driver);
    siteSearchResults.waitForPageToLoad();
    String found = siteSearchResults.findExactMatchString();

    assertTrue(found.contentEquals(geneId), "Expected Exact Match to " + geneId + "but found="+ found);
    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);

}

  
  
  public JSONObject parseEndpoint (String url, String rootName)  {
    this.driver.get(url);
    Service servicePage = new Service(driver);
    String jsonContent = servicePage.jsonContent();
    return new JSONObject("{ \"" + rootName + "\":" + jsonContent + "}");
  }
  
}
