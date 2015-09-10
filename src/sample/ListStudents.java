package sample;


import Project.Pair;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ListStudents implements Initializable {

  public TableView listStudentsWithCourses;
  private HashMap <String, LinkedList<String>> idAndName = new HashMap();
  private LinkedList<String> courses = new LinkedList<String>();
  private HashMap <String, String> nameFromId = new HashMap();

  public String[][] genTable() {
    if (idAndName.size() == 0)
      return null;
    String[][] res = new String[idAndName.size() + 1][courses.size() + 2];
    int x = 1;
    res[0][0] = "ID";
    res[0][x++] = "Name";
    for (String course: courses) {
      res[0][x++] = course;
    }
    for (int i = 1; i <= idAndName.size(); i++) {
      String id = Integer.toString(i);
      x = 0;
      res[i][x++] = id;
      res[i][x++] = nameFromId.get(id);
      for (String course: courses) if (idAndName.containsKey(nameFromId.get(id))) {
        if (idAndName.get(nameFromId.get(id)).contains(course)) {
          res[i][x++] = "√";
        } else {
          res[i][x++] = "X";
        }
      }
    }
    return res;
  }

  public void showTableView() {
    String[][] staffArray = genTable();
    if (staffArray == null) { return; }
    listStudentsWithCourses.getColumns().clear();
    ObservableList<String[]> data = FXCollections.observableArrayList();
    data.addAll(Arrays.asList(staffArray));
    data.remove(0);
    for (int i = 0; i < staffArray[0].length; i++) {
      TableColumn tc = new TableColumn(staffArray[0][i]);
      final int colNo = i;
      tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
          return new SimpleStringProperty((p.getValue()[colNo]));
        }
      });
      tc.setPrefWidth(90);
      listStudentsWithCourses.getColumns().add(tc);
    }
    listStudentsWithCourses.setItems(data);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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
      }
      showTableView();
    } catch(IOException ignored) {}
  }
}

