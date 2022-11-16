package org.apidb.eupathsitecommon.watar.pages;

public class CheckDatabaseCategoryExists {
  
  public static boolean containsError(boolean isNotNull) {
    
	if(!isNotNull) {
        return true;
    }
  
    else {  
        return false;
    }
  
  }
 
}