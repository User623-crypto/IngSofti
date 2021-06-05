package zextra.components.jfx_list_component;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Course;

public class CourseCell extends ListCell<Course> {
    private HBox hbox = new HBox();
    private Button btn = new Button("Testing");
    private Pane pane = new Pane();
    private final Label label = new Label("");

    public CourseCell(){
        super();
        this.hbox.getChildren().addAll(label,pane,btn);
        this.hbox.setHgrow(pane, Priority.ALWAYS);
    }

    @Override
    public void updateItem(Course item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item != null) {
            label.setText(item.toString());

            System.out.println(item);
            setGraphic(hbox);
        }
    }
}
