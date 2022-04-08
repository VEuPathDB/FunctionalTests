package org.apidb.eupathsitecommon.watar;

//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;     
import org.openqa.selenium.Dimension;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

//import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apidb.eupathsitecommon.watar.pages.DatasetPage;
import org.apidb.eupathsitecommon.watar.pages.GeneRecordPage;
import org.apidb.eupathsitecommon.watar.pages.GenesByLocusTagSearchPage;
import org.apidb.eupathsitecommon.watar.pages.GenesByTaxonSearchPage;
import org.apidb.eupathsitecommon.watar.pages.HomePage;
import org.apidb.eupathsitecommon.watar.pages.LoginPage;
import org.apidb.eupathsitecommon.watar.pages.SearchForm;
import org.apidb.eupathsitecommon.watar.pages.SearchResultsPage;
import org.apidb.eupathsitecommon.watar.pages.SequenceRetrievalTool;
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
    ChromeOptions option=new ChromeOptions();
    option.addArguments("headless","--window-size=1000,1000");
    //option.setHeadless(false);
    this.driver = new ChromeDriver(option);
    //driver.manage().window().setSize(new Dimension(1000, 1000));
    LoginPage loginPage = new LoginPage(driver, username, password);
    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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
 
  @DataProvider(name = "checkDatabaseCategoryExists")
  public Iterator<Object[]> checkDatabaseCategoryExists() {
  ArrayList<Object[]> returnArray = new ArrayList<Object[]>();
  JSONObject datasetsObj = (JSONObject) parseObjectToObject(Utilities.CHECK_DATABASE_CATEGORY);
  JSONArray recordsArray = (JSONArray) datasetsObj.get("records");
  for (int i=0; i < recordsArray.length(); i++) {
    JSONObject record = (JSONObject) recordsArray.get(i); 
    JSONObject attributesObj = (JSONObject) record.getJSONObject("attributes");
    JSONArray idArray = (JSONArray) record.getJSONArray("id");
    JSONObject idValues = (JSONObject) idArray.get(0);
    String datasetId = idValues.getString("value");
    Object newCategory = attributesObj.get("newcategory");
    boolean isNotNull = newCategory instanceof String;
    Object[] sa = new Object[2];
    sa[0] = isNotNull;
    sa[1] = datasetId;
    returnArray.add(sa);
    }
    return returnArray.iterator();
  }

  @Test(dataProvider="checkDatabaseCategoryExists",
  	  groups = { "functional_tests" } )
  public void testDatabaseCategoryExists (boolean isNotNull, String datasetId) {
    assertTrue(isNotNull, "Category doesn't exist for " + datasetId);
  }
  
  @DataProvider(name= "createStrandSpecificRNASeqProfile")
  public Iterator<Object[]> createStrandSpecificRNASeqProfile() {
    
    ArrayList<Object[]> finalReturnList = new ArrayList<Object[]>();
    JSONObject datasetsObj = (JSONObject) parseObjectToObject(Utilities.RNA_SEQ_PROFILE);
    JSONArray recordsArray = (JSONArray) datasetsObj.get("records");
    
    for (int i=0;i<recordsArray.length();i++) {
      HashMap<String,Float> sampleValueFirstStrand = new HashMap<String, Float>();
      HashMap<String,Float> sampleValueSecondStrand = new HashMap<String, Float>();
      ArrayList<String> switchStrandsArrayList = new ArrayList<String>();
      ArrayList<String> datasetIdArrayList = new ArrayList<String>();
      JSONObject record = (JSONObject) recordsArray.get(i);
      JSONObject tablesObj = (JSONObject) record.getJSONObject("tables");
      JSONArray rnaSeqSamplesInternal = tablesObj.getJSONArray("RNASeqSamplesInternal");
      
      for (int j = 0; j<rnaSeqSamplesInternal.length();j++) {
        JSONObject currentRnaSeqSample = (JSONObject) rnaSeqSamplesInternal.get(j);
        String studyName = currentRnaSeqSample.getString("study_name");
        String averageValue = currentRnaSeqSample.getString("average_value");
        Float averageValueFloat = Float.valueOf(averageValue).floatValue();
        String sampleName = currentRnaSeqSample.getString("sample");
        String switchStrands = currentRnaSeqSample.getString("switch_strands");
        String datasetId = currentRnaSeqSample.getString("dataset_id");
				        
        if (studyName.contains("firststrand")) {
          sampleValueFirstStrand.put(sampleName,averageValueFloat);
          switchStrandsArrayList.add(switchStrands);
          datasetIdArrayList.add(datasetId);
        }
 
        else if (studyName.contains("secondstrand")) {
          sampleValueSecondStrand.put(sampleName,averageValueFloat);
	  switchStrandsArrayList.add(switchStrands);
	  datasetIdArrayList.add(datasetId);
        }
							          
        else {
          continue;
        }
      }
      if (sampleValueFirstStrand.size()>0 && sampleValueSecondStrand.size()>0 && switchStrandsArrayList.size()>0) {
        Object[] sa = new Object[4];
        sa[0] = sampleValueFirstStrand;
        sa[1] = sampleValueSecondStrand;
        sa[2] = switchStrandsArrayList;
        sa[3] = datasetIdArrayList;
        finalReturnList.add(sa);
      }
    }
    return finalReturnList.iterator();
  }
  
  @Test(dataProvider = "createStrandSpecificRNASeqProfile")
  public void rnaSeqProfile (HashMap<String,Float> sampleValueFirstStrand, HashMap<String,Float> sampleValueSecondStrand, ArrayList<String> switchStrandArrayList, ArrayList<String> datasetIdArrayList) {
    String firstSwitchStrand = switchStrandArrayList.get(1);
    String firstDatasetId = datasetIdArrayList.get(0);
    
    for (int j = 0; j<datasetIdArrayList.size(); j++) {
      String datasetId = datasetIdArrayList.get(j);
      assertTrue(firstDatasetId.equals(datasetId), "Dataset IDs changed mid dataset " + firstDatasetId);
    }
    
    assertTrue(sampleValueFirstStrand.size()==sampleValueSecondStrand.size(), "Number of first and second strands are not equal");
    
    for (int k = 0; k<switchStrandArrayList.size(); k++) {
      String switchStrand = switchStrandArrayList.get(k);
      assertTrue(firstSwitchStrand.equals(switchStrand), "switch_strand changed mid dataset " + firstDatasetId);
    } 
    
    for (String i : sampleValueFirstStrand.keySet()) {
      Float averageValueFirstStrand = sampleValueFirstStrand.get(i);
      Float averageValueSecondStrand = sampleValueSecondStrand.get(i);
      if (firstSwitchStrand.equals("false")) {
        assertTrue(averageValueFirstStrand>averageValueSecondStrand, "Second Strand greater than First Strand " + firstDatasetId);
      } 
      else if (firstSwitchStrand.equals("true")) {
        assertTrue(averageValueFirstStrand<averageValueSecondStrand, "First Strand greater than Second Strand " + firstDatasetId);
      }
    }
  }

  
  @DataProvider(name = "createDatasetReferences")
  public Iterator<Object[]> createDatasetReferences() {
    ArrayList<Object[]> finalReturnList = new ArrayList<Object[]>();
    HashMap<String,Integer> allSearches = new HashMap<String, Integer>();
    JSONArray searchTypesArray = parseObjectToArray("/service/record-types");
    
    for (int i=0;i<searchTypesArray.length(); i++) {
      String recordType = searchTypesArray.getString(i);
      JSONArray searchesArray = parseObjectToArray("/service/record-types/" + recordType + "/searches");
     
      for(int j = 0; j < searchesArray.length(); j++) {
        JSONObject search = (JSONObject) searchesArray.get(j);
        String fullName = (String) search.get("fullName");
        allSearches.put(fullName, 1); 
      }
    }
    JSONObject datasetsObj = parseObjectToObject(Utilities.ALL_SEARCHES);
    JSONArray recordsArray = (JSONArray) datasetsObj.get("records");
    
    for(int i = 0; i < recordsArray.length(); i++) {
      ArrayList<String> targetNamesArrayList = new ArrayList<String>();
      JSONObject record = (JSONObject) recordsArray.get(i);
      JSONArray idArray = (JSONArray) record.get("id");
      JSONObject id = (JSONObject) idArray.get(0);
      String datasetId = id.getString("value");  
      JSONObject tables = (JSONObject) record.get("tables");
      JSONArray referencesArray = (JSONArray) tables.get("References"); 
	  
      for(int j = 0; j < referencesArray.length(); j++) {
        JSONObject currentReference = (JSONObject) referencesArray.get(j);
        Object targetType = currentReference.get("target_type");
        Object targetName = currentReference.get("target_name");
        if (targetType instanceof String && targetType.equals("question") && targetName instanceof String) {
          String finaltargetName = currentReference.getString("target_name");
          targetNamesArrayList.add(finaltargetName);
        }
       }
       Object[] sa = new Object[3];
       sa[0] = datasetId;
       sa[1] = targetNamesArrayList;
       sa[2] = allSearches;
       finalReturnList.add(sa);
    }
    return finalReturnList.iterator();
  }

  @Test(dataProvider="createDatasetReferences")
  public void datasetReferences (String datasetId, ArrayList<String> targetNamesArrayList, HashMap<String, Integer> allSearches) {
    for(int j = 0; j < targetNamesArrayList.size(); j++) {
      String currentTargetName = targetNamesArrayList.get(j);
      assertTrue(allSearches.containsKey(currentTargetName), "Name not found in HashMap " + datasetId);
    }
  }
 
  @DataProvider(name = "searches")
  public Iterator<Object[]> createSearches() {
    
    ArrayList<Object[]> searchesArrayList = new ArrayList<Object[]>();
    JSONArray recordTypesArray = parseObjectToArray("/service/record-types");
    
    for(int i = 0; i < recordTypesArray.length(); i++) {
      String recordType = recordTypesArray.getString(i);
      JSONArray searchesArray = parseObjectToArray("/service/record-types/" + recordType + "/searches");
       
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
          urlSegment.contains("boolean_question"))
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
    
    JSONObject datasetsJson = parseEndpoint(baseurl + Utilities.DATASETS_SEARCHES, "datasets");
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

  @Test(description="Checking for unique track key names")
  public void jbrowseUniqueKeys() {
    SoftAssert softAssert = new SoftAssert();
    HashMap<String, Integer> keyCount = createtrackKeys();
    for (String i : keyCount.keySet()){
      int count = keyCount.get(i);
      softAssert.assertTrue(count == 1, "Key " + i + " used " + count + " times");
    }
    softAssert.assertAll();
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
/*
  @Test(dataProvider="datasets", 
      description="Assert dataset page loads without error.  Checks for cross-refs of wdkSearches",
      groups = { "functional_tests" })
  public void datasetPage (String datasetPageUrl, String datasetId) {
    DatasetPage datasetPage = new DatasetPage(driver, datasetPageUrl);
    datasetPage.waitForPageToLoad();
    assertTrue(!datasetPage.containsError(), "Failure on DatasetPage: " + datasetId);
  }
  */

  @Test(description="Performance Test Filter Param Search Form",
        groups = { "functional_tests", "performance_tests" })
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
    searchForm.closeBanner();
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
  public void geneSrtTool () {

    SequenceRetrievalTool srt = new SequenceRetrievalTool(this.driver, this.baseurl);
    srt.waitForPageToLoad();
    
    long startTime = System.nanoTime();        
    srt.submit(); // submit the default

    Service srtResult = new Service(this.driver);
    String fastaContent = srtResult.pageContent();

    assertTrue(fastaContent.startsWith(">"), "Defline of fasta file should start with >");
    assertTrue(fastaContent.length() > 500, "FASTA file should have some content");
    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    Reporter.log(Utilities.PAGE_LOAD_TIME + "=" + duration);
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
    String jsonContent = servicePage.pageContent();
    return new JSONObject("{ \"" + rootName + "\":" + jsonContent + "}");
  }
  
  
  public HashMap<String,Integer> createtrackKeys() {
    HashMap<String,Integer> keyCounts = new HashMap<String, Integer>();
    JSONObject tracklistArray = parseObjectToObject(Utilities.TRACK_KEYS);
    JSONArray typesArray = (JSONArray) tracklistArray.get("include");
    
    for (int i = 0; i < typesArray.length(); i++) {
      if (i != 0 && i != 6) {
        String urlPiece = (String) typesArray.get(i); 
        JSONObject jbrowseTracks = parseEndpoint(System.getProperty("baseurl") + urlPiece, "track"); 
        JSONObject tracksObj = (JSONObject) jbrowseTracks.get("track");
        JSONArray tracksArray = (JSONArray) tracksObj.get("tracks");
        
        for (int j = 0; j < tracksArray.length(); j++) {
          JSONObject track = (JSONObject) tracksArray.get(j);
	  String key = track.getString("key");
	  if (keyCounts.containsKey(key)) {
	    int count = keyCounts.get(key);
	    keyCounts.remove(key);
	    keyCounts.put(key, count + 1);
	  }
	  else {
	  keyCounts.put(key, 1);
	  }
	  }	
      }
    }
    return keyCounts;
  } 
  
  public JSONArray parseObjectToArray(String urlPiece) {
    JSONObject obj = parseEndpoint(baseurl + urlPiece, "url");
    JSONArray ary = (JSONArray) obj.get("url");
    return ary;
  }
  
  public JSONObject parseObjectToObject(String urlPiece) {
    JSONObject obj = parseEndpoint(baseurl + urlPiece, "urlName");
    JSONObject objParsed = (JSONObject) obj.get("urlName");
    return objParsed;
  }
  
}
