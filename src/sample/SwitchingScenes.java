package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class SwitchingScenes {
  public void goToNextScene(String name, ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(name));
    Scene s = new Scene(root);
    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
    stage.close();
    stage.setScene(s);
    if (name.contains("studentFrame")) {
      stage.setTitle("Student Project");
    } else if (name.contains("encryption")){
      stage.setTitle("Encryption-Decryption Project");
    } else if (name.contains("secondFrame")) {
      stage.setTitle("ABOUT-US");
    } else if (name.contains("mainFrame")) {
      stage.setTitle("Please Login!");
    }
    stage.show();
  }

}
