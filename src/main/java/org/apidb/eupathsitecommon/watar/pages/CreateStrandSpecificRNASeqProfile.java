package org.apidb.eupathsitecommon.watar.pages;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateStrandSpecificRNASeqProfile {
  
  public static boolean checkDatasetIdConsistency (String firstDatasetId, int j, ArrayList<String> datasetIdArrayList) {
	
	String datasetId = datasetIdArrayList.get(j);
	
	if(firstDatasetId.equals(datasetId)) {
        return true;
    }
    else {  
        return false;
    }
  }
  
  public static boolean checkStrandSize (HashMap<String,Float> sampleValueFirstStrand, HashMap<String,Float> sampleValueSecondStrand) {
		if(sampleValueFirstStrand.size()==sampleValueSecondStrand.size()) {
	        return true;
	    }
	    else {  
	        return false;
	    }
	  }
 
  public static boolean checkStrandConsistency (String firstSwitchStrand, ArrayList<String> switchStrandArrayList, int k) {
	String switchStrand = switchStrandArrayList.get(k);
    if(firstSwitchStrand.equals(switchStrand)) {
	    return true;
	}
	else {  
	    return false;
	}
  }
  
  public static boolean checkAverageValues (HashMap<String,Float> sampleValueFirstStrand, HashMap<String,Float> sampleValueSecondStrand, String firstSwitchStrand, String i) {
	Float averageValueFirstStrand = sampleValueFirstStrand.get(i);
    Float averageValueSecondStrand = sampleValueSecondStrand.get(i);
    if (firstSwitchStrand.equals("false")) {
    	if(averageValueFirstStrand>averageValueSecondStrand) {
            return true;
        }
    	else {  
    	    return false;
        }
    } 
    else if (firstSwitchStrand.equals("true")) {
    	if(averageValueSecondStrand>averageValueFirstStrand) {
            return true;
        }
    	else {  
    	    return false;
        }
    }
    else {
        return false;	
    }
	
  }  
}