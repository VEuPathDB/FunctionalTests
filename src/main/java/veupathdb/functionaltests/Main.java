package veupathdb.functionaltests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class Main {

  private static final String CONFIG = "smoketest_ng.xml";

  public static void main(String[] args) throws Exception {
    System.out.println("Main is runnable.");
    System.out.println("Testing dependencies were bundled.");
    new JSONObject().keys();
    System.out.println("Testing resources included and accessible.");
    try (InputStream resource = Main.class.getClassLoader().getResourceAsStream(CONFIG);
         BufferedReader br = new BufferedReader(new InputStreamReader(resource))) {
      System.out.println(br.readLine());
    }
  }
}
