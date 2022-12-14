package org.apidb.eupathsitecommon.watar.pages;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateDatasetReferences {
  
  public static String checkAllSearchesContainsTargetName (HashMap<String, Integer> allSearches, ArrayList<String> targetNamesArrayList, int j) {
	
	String currentTargetName = targetNamesArrayList.get(j);
      
	if(allSearches.containsKey(currentTargetName)) {
        return "pass";
    }
    else {  
        return currentTargetName;
    }
  
  }
  
}