package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class secondFrame extends SwitchingScenes {

  public void onClickEncryptionProject(ActionEvent actionEvent) throws IOException {
    goToNextScene("../Views/encryptionFrame.fxml", actionEvent);
  }

  public void onClickStudentProject(ActionEvent actionEvent) throws IOException {
    goToNextScene("../Views/studentFrame.fxml", actionEvent);
  }

  public void OnClickExit(ActionEvent actionEvent) {
    System.exit(0);
  }

  public void OnClickBack(ActionEvent actionEvent) throws IOException {
    goToNextScene("../Views/mainFrame.fxml", actionEvent);
  }
}
