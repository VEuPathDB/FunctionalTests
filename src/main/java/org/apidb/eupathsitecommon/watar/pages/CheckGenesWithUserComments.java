package org.apidb.eupathsitecommon.watar.pages;

public class CheckGenesWithUserComments {
  
  public static boolean containsError(int commentCount) {
    
	if(commentCount <= 0) {
        return true;
    }
  
    else {  
        return false;
    }
  
  }
 
}