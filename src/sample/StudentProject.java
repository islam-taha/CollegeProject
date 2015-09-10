package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

public class StudentProject extends SwitchingScenes implements Initializable {

  public TextField courseNameField;
  public TextField courseHoursField;
  public Button addCourseButton;
  public Button listAvailableCoursesButton;
  public TextArea courseListArea;
  public TextField studentIdField;
  public TextField studentNameField;
  public ComboBox<String> courseListMenu;
  public Button addStudentWithCourseButton;
  public Button statisticsButton;
  public BarChart barChart;
  public Label idAndNameLabel;
  public PieChart pieChart;

  private HashMap <String, Integer> courseData = new HashMap<String, Integer>();
  private HashMap <String, Integer> courseStat = new HashMap<String, Integer>();
  private HashMap <String, String> nameFromId = new HashMap();
  private HashMap <String, String> idFromName = new HashMap<String, String>();
  private HashMap <String, LinkedList<String>> idAndName = new HashMap();

  private LinkedList <String> courses = new LinkedList();

  private int maxID = 0;

  public void OnClickExit(ActionEvent actionEvent) {
    System.exit(0);
  }

  public void OnClickAddCourse(ActionEvent actionEvent) {
    String courseInfo = courseNameField.getText() + " " + courseHoursField.getText();
    String courseName = courseNameField.getText();
    String courseHour = courseHoursField.getText();
    if (courseName != null && courseHour != null && courseName.length() > 0 && courseHour.length() > 0) {
      if (!courseHour.matches("[0-9]+")) {
        courseListArea.setText("Course Hours is not a valid integer!!!");
        return;
      }
      if (courseData.containsKey(courseName))
        courseListArea.setText("Course Already exists!!!");
      if (!courseListMenu.getItems().contains((String)courseName))
        courseListMenu.getItems().add((String)courseName);
      if (courseData.containsKey(courseName)) {
        courseData.put(courseName, courseData.get(courseName) + 1);
      } else {
        courseData.put(courseName, 1);
      }
      courseNameField.clear();
      courseHoursField.clear();
      courseListArea.clear();
    } else {
      courseListArea.setText("Please Enter valid course name and period...");
    }
  }

  public void OnClickListAvailableCourses(ActionEvent actionEvent) {
    int i = 1;
    String text = "";
    for (Map.Entry<String, Integer> entry : courseData.entrySet()) {
      text += "" + i + ". " + entry.getKey() + "\n";
      i++;
    }
    if (text.length() > 0)
      courseListArea.setText(text);
    else
      courseListArea.setText("There is no available courses");
  }

  public void OnClickStatisticsButton(ActionEvent actionEvent) {
    try {
      BarChart.Series[] sr = new BarChart.Series[courseStat.size()];
      PieChart.Data[] da = new PieChart.Data[courseStat.size()];
      int i = 0;
      for (Map.Entry<String, Integer> entry : courseStat.entrySet()) {
        sr[i] = new BarChart.Series(entry.getKey(), FXCollections.observableArrayList(
                new BarChart.Data(entry.getKey(), (double) entry.getValue())
        ));
        da[i] = new PieChart.Data(entry.getKey(), entry.getValue());
        i++;
      }
      ObservableList barChartData = FXCollections.observableArrayList(sr);
      ObservableList pieCharData = FXCollections.observableArrayList(da);
      barChart.setData(barChartData);
      barChart.setBarGap(2d);
      pieChart.setData(pieCharData);
    } catch (Exception ignore) {}
  }

  public void writeStudentsWithCourses() {
    try {
      BufferedWriter bw = new BufferedWriter(
              new FileWriter("students.txt", true)
      );
      bw.append(studentNameField.getText() + " " + studentIdField.getText() + " " + courseListMenu.getValue());
      bw.close();
    } catch(IOException ignored) {}
  }

  public void writeToFile() {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter("students", true));
      bw.append("\n" + studentIdField.getText() + " " + studentNameField.getText() + " " + courseListMenu.getValue());
      bw.close();
    } catch(IOException ignore) {}

  }

  public void OnClickAddStudentWithCourse(ActionEvent actionEvent) {
    String id = studentIdField.getText();
    String sc = studentNameField.getText();
    String selectedCourse = courseListMenu.getValue();
    idAndNameLabel.setText("Please Enter Valid id & name");
    if (id != null && sc != null && selectedCourse != null) {
      if (id.length() > 0 && sc.length() > 0) {
        if (!sc.matches("[a-zA-Z]+") || !id.matches("[0-9]+")) {
          idAndNameLabel.setVisible(true);
          return;
        }
        if (courseStat.containsKey(selectedCourse)) {
          courseStat.put(selectedCourse, courseStat.get(selectedCourse) + 1);
        } else {
          courseStat.put(selectedCourse, 1);
        }
        int curId = Integer.parseInt(id);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (curId > maxID) {
          if (idFromName.containsKey(sc)) {
            alert.setContentText(sc + " has id = " + idFromName.get(sc));
            alert.showAndWait();
            return;
          } else {
            idFromName.put(sc, Integer.toString(maxID + 1));
            studentIdField.setText("" + (maxID + 1));
            maxID++;
            alert.setContentText("Student " + sc + " created with id = " + (maxID));
            alert.showAndWait();
          }
        } else {
          if (idFromName.containsKey(sc)) {
            if (!idFromName.get(sc).equals(id)) {
              alert.setContentText(sc + " has id = " + idFromName.get(sc));
              alert.showAndWait();
              return;
            }
          } else {
            idFromName.put(sc, Integer.toString(maxID + 1));
            nameFromId.put(Integer.toString(maxID + 1), sc);
            studentIdField.setText("" + (maxID + 1));
            maxID++;
            alert.setContentText("Student " + sc + " created with id = " + maxID);
            alert.showAndWait();
          }
        }
        writeToFile();
        idAndNameLabel.setVisible(false);
        if (idAndName.containsKey(sc)) {
          if (idAndName.get(sc).contains(courseListMenu.getValue())) {
            return;
          }
          idAndName.get(sc).add(courseListMenu.getValue());
        } else {
          idAndName.put(sc, new LinkedList<String>());
          idAndName.get(sc).add(courseListMenu.getValue());
        }
        try {
          BarChart.Series[] sr = new BarChart.Series[courseStat.size()];
          PieChart.Data[] da = new PieChart.Data[courseStat.size()];
          int i = 0;
          for (Map.Entry<String, Integer> entry : courseStat.entrySet()) {
            sr[i] = new BarChart.Series(entry.getKey(), FXCollections.observableArrayList(
                    new BarChart.Data(entry.getKey(), (double) entry.getValue())
            ));
            da[i] = new PieChart.Data(entry.getKey(), entry.getValue());
            i++;
          }
          ObservableList barChartData = FXCollections.observableArrayList(sr);
          ObservableList pieCharData = FXCollections.observableArrayList(da);
          barChart.setData(barChartData);
          barChart.setBarGap(2d);
          pieChart.setData(pieCharData);
        } catch (Exception ignore) {}
      } else {
        idAndNameLabel.setVisible(true);
      }
    } else {
      idAndNameLabel.setVisible(true);
    }
    addStudentWithCourseButton.setFocusTraversable(false);
  }

  public void OnClickBack(ActionEvent actionEvent) throws IOException {
    goToNextScene("../Views/secondFrame.fxml", actionEvent);
  }

  public void OnClickStudentStats(ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("../Views/listStudents.fxml"));
    Scene s = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(s);
    stage.setTitle("Students stats");
    stage.show();
  }

  public void readData() {
    try {
      BufferedReader br = new BufferedReader(new FileReader("students"));
      while (br.ready()) {
        String[] line = br.readLine().split(" ");
        if (line.length != 3) continue;
        if (!courses.contains(line[2])) {
          courses.add(line[2]);
        }
        if (!idAndName.containsKey(line[1])) {
          LinkedList<String> tmp = new LinkedList();
          tmp.add(line[2]);
          idAndName.put(line[1], tmp);
        } else if (!idAndName.get(line[1]).contains(line[2])) {
          idAndName.get(line[1]).add(line[2]);
        }
        nameFromId.put(line[0], line[1]);
        idFromName.put(line[1], line[0]);
        if (courseStat.containsKey(line[2])) {
          courseStat.put(line[2], courseStat.get(line[2]) + 1);
        } else {
          courseStat.put(line[2], 1);
        }
        maxID = Math.max(Integer.parseInt(line[0]), maxID);
      }
    } catch(IOException ignored) {}

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    readData();
    for (String course: courses) {
      if (!courseListMenu.getItems().contains((String)course))
        courseListMenu.getItems().add((String)course);
      if (!courseData.containsKey(course)) {
        courseData.put(course, 1);
      } else {
        courseData.put(course, courseData.get(course) + 1);
      }
    }
    try {
      BarChart.Series[] sr = new BarChart.Series[courseStat.size()];
      PieChart.Data[] da = new PieChart.Data[courseStat.size()];
      int i = 0;
      for (Map.Entry<String, Integer> entry : courseStat.entrySet()) {
        sr[i] = new BarChart.Series(entry.getKey(), FXCollections.observableArrayList(
                new BarChart.Data(entry.getKey(), (double) entry.getValue())
        ));
        da[i] = new PieChart.Data(entry.getKey(), entry.getValue());
        i++;
      }
      ObservableList barChartData = FXCollections.observableArrayList(sr);
      ObservableList pieCharData = FXCollections.observableArrayList(da);
      barChart.setData(barChartData);
      barChart.setBarGap(2d);
      pieChart.setData(pieCharData);
    } catch (Exception ignore) {}
  }
}
