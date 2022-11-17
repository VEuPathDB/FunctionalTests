package org.apidb.eupathsitecommon.watar.pages;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateStrandSpecificRNASeqProfile {
  
  public static boolean checkDatasetIdConsistency (String firstDatasetId, int j, ArrayList<String> datasetIdArrayList) {
	
	String datasetId = datasetIdArrayList.get(j);
	
	boolean result = (firstDatasetId.equals(datasetId)) ? true : false;
    return result;
	
  }
  
  public static boolean checkStrandSize (HashMap<String,Float> sampleValueFirstStrand, HashMap<String,Float> sampleValueSecondStrand) {
	
	boolean result = (sampleValueFirstStrand.size()==sampleValueSecondStrand.size()) ? true : false;
	return result;
	
  }
 
  public static boolean checkStrandConsistency (String firstSwitchStrand, ArrayList<String> switchStrandArrayList, int k) {
	
	String switchStrand = switchStrandArrayList.get(k);
    
	boolean result = (firstSwitchStrand.equals(switchStrand)) ? true : false;
	return result;
	
  }
  
  public static boolean checkAverageValues (HashMap<String,Float> sampleValueFirstStrand, HashMap<String,Float> sampleValueSecondStrand, String firstSwitchStrand, String i) {
	
	Float averageValueFirstStrand = sampleValueFirstStrand.get(i);
    Float averageValueSecondStrand = sampleValueSecondStrand.get(i);
    
    if (firstSwitchStrand.equals("false")) {
    	boolean result = (averageValueFirstStrand>averageValueSecondStrand) ? true : false;
        return result;
    } 
    
    else if (firstSwitchStrand.equals("true")) {
    	boolean result = (averageValueSecondStrand>averageValueFirstStrand) ? true : false;
        return result;
    }
    
    else {
        return false;	
    }
	
  }  
}