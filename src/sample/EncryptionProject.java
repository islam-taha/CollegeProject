package sample;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;

import Project.*;

public class EncryptionProject extends SwitchingScenes {

  public TextField filePath;
  public TextField keyValue;
  public TextArea showFileArea;
  public CheckBox encBox;
  public CheckBox decBox;
  public Button browseButton;
  public Button encDecButton;
  public Button saveAsButton;
  public ProgressIndicator encDecIndicator;
  public TextArea decArea;

  private File file = null;

  public void OnCheckEncrypt(ActionEvent actionEvent) {
    decBox.setSelected(false);
  }

  public void OnCheckDecrypt(ActionEvent actionEvent) {
    encBox.setSelected(false);
  }

  public void OnClickBrowse(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
    if (file != null)
      filePath.setText(file.getName());
  }

  public String fileCurContents() {
    String cur = "";
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      do {
        String line = br.readLine();
        if (line == null) break;
        cur += line + "\n";
      } while(br.ready());
    } catch(Exception ignored) {}
    return cur;
  }

  private boolean validateIntegers(String input) {
    boolean f = true;
    for (char ch: input.toCharArray()) {
      f &= ('0' <= ch && ch <= '9');
    }
    return f;
  }

  public void OnClickEncDec(ActionEvent actionEvent) throws IOException, InterruptedException {
    String fileContents = "";
    if (file != null && keyValue.getText() != null) {
      if (!validateIntegers(keyValue.getText())) {
        showFileArea.setText("The Key Value is not an integer!!");
        return;
      }
      if (encBox.isSelected() || decBox.isSelected()) {
        if (encBox.isSelected()) {
          Encryption encryption = new Encryption();
          fileContents = encryption.performOperation(file.getPath(), Integer.parseInt(keyValue.getText()));
          decArea.setText(fileContents);
          showFileArea.setText(fileCurContents());
        } else {
          Decryption decryption = new Decryption();
          fileContents = decryption.performOperation(file.getPath(), Integer.parseInt(keyValue.getText()));
          showFileArea.setText((fileCurContents()));
          decArea.setText(fileContents);
        }
      } else {
        showFileArea.setText("Please choose either operation!");
      }
    } else {
      showFileArea.setText("Please Choose a file and Enter the required key for Encryption or Decryption");
    }
  }

  public void OnClickExit(ActionEvent actionEvent) {
    System.exit(0);
  }

  public void OnClickSaveAs(ActionEvent actionEvent) throws IOException {
    FileChooser fileChooser = new FileChooser();

    File curFile = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());

    if (curFile != null) {
      try {
        curFile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(curFile));
        bw.write(decArea.getText());
        bw.close();
        showFileArea.setText(curFile.getName() + " Saved...");
      } catch(IOException io) {
        showFileArea.setText("Something Went Wrong during saving " + curFile.getName());
      }
    }
  }

  public void OnClickBack(ActionEvent actionEvent) throws IOException {
    goToNextScene("../Views/secondFrame.fxml", actionEvent);
  }
}
