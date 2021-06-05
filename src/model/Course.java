package model;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.dao.CourseDao;
import zextra.SceneChanger;

public class Course {

    private int id;
    private String name;
    private int day;
    private String time;

    public Course(String name, int day, String time) {
        this.name = name;
        this.day = day;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static void initCoursesTable(TableView<Course> coursesTable, TableColumn<Course,String> nameCol,
                                          ObservableList<Course> courses)
    {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        coursesTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Course a = coursesTable.getSelectionModel().getSelectedItem();
                    if (a == null) return;
                    try{
                        new SceneChanger().createStage("/view/courses/MainView.fxml","Course",a, coursesTable);

                    }catch (Exception ignore){}
                }
            }
        });
        coursesTable.setItems(courses);
        try {
            courses.addAll(new CourseDao().readAllCourses());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
