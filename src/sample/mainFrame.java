package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class mainFrame extends Application {

  public TextField userName;
  public PasswordField userPassword;
  public Label sorryLabel;
  public Label userNameLabel;
  public Label passwordLabel;
  public TextField filePathOnBrowsing;

  private File file;

  public void loginButtonOnClick(ActionEvent actionEvent) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("../Views/secondFrame.fxml"));
    Scene s = new Scene(root);
    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
    if (true) {
      sorryLabel.setVisible(false);
      stage.close();
      stage.setScene(s);
      stage.setTitle("ABOUT-US");
      stage.show();
    } else {
      sorryLabel.setVisible(true);
    }
  }

  public boolean validUserNameAndPasswords() {
    String formSavedStyle = "username: " + userName.getText() + " password: " + userPassword.getText();
    BufferedReader br = null;
    if (file == null) return false;
    try {
      br = new BufferedReader(new FileReader(file));
      do {
        if (formSavedStyle.equals(br.readLine()))
          return true;
      } while (br.ready());
    } catch(IOException io) {}
    return false;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("../Views/mainFrame.fxml"));
    primaryStage.setTitle("Please Login!");
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public void OnClickBrowse(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();

    file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
    if (file != null)
      filePathOnBrowsing.setText(file.getName());
  }

  public void OnClickExit(ActionEvent actionEvent) {
    System.exit(0);
  }
}

