package org.apidb.eupathsitecommon.watar.pages;

import java.util.HashMap;

public class LegacyDatasets {
  
  public static boolean legacyIdIsMapped (HashMap<String, String> productionIdName, HashMap<String, String> legacyIdName, String i) {
	
	String j = legacyIdName.get(i);
	
	boolean result = (productionIdName.containsKey(j)) ? true : false;
    return result;
	
  }
    
}