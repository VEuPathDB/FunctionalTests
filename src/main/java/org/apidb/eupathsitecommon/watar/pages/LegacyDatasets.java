package org.apidb.eupathsitecommon.watar.pages;

import java.util.HashMap;

public class LegacyDatasets {
  
  public static boolean legacyIdIsMapped (HashMap<String, String> productionIdName, HashMap<String, String> legacyIdName, String i) {
	
	String j = legacyIdName.get(i);
	
	if(productionIdName.containsKey(j)) {
        return true;
    }
    else {  
        return false;
    }
  }
    
}