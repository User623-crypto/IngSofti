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

    /**
     * Course constructor
     * @param name The name of the course
     * @param day A day in the weekend 1 to 7
     * @param time The time the course starts
     */
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

    /**
     * This function initialize a TableView with all the courses from the db
     * @param coursesTable The GUI element that will be initialized
     * @param nameCol The col where it will write the course name
     * @param courses The list that will hold the courses
     */
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

    /**
     * This function initialize a TableView with  the courses from the db for a certain user
     * @param coursesTable The GUI element that will be initialized
     * @param nameCol The col where it will write the course name
     * @param courses The list that will hold the courses
     * @param userId the user id that will has inrolled in the courses
     */
    public static void initCoursesTable(TableView<Course> coursesTable, TableColumn<Course,String> nameCol,
                                        ObservableList<Course> courses,int userId)
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
            courses.addAll(new CourseDao().readAllUserCourses(userId));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
