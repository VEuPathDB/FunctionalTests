package org.apidb.eupathsitecommon.watar.pages;

public class CheckGenesWithUserComments {
  
  public static boolean containsError(int commentCount) {
    
	boolean result = (commentCount <= 0) ? true : false;
    return result;
	
  }
 
}