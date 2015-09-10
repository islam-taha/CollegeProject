package Project;

import java.io.*;

public class Encryption implements EncDec {
  @Override
  public String performOperation(String path, int key) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    String readFile = "";
    do {
      String line = br.readLine();
      for (int i = 0; i < line.length(); i++) {
        readFile += (char)(((int)line.charAt(i) + key));
      }
      readFile += "\n";
    } while(br.ready());
    return readFile;
  }
}
