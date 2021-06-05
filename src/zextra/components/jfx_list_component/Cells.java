package zextra.components.jfx_list_component;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Cells extends ListCell<String>{

        private  HBox hbox = new HBox();
        private Button btn = new Button("Testing");
        private Pane pane = new Pane();
        private Label label = new Label("");

        public Cells(){
            super();
            this.hbox.getChildren().addAll(label,pane,btn);
//            hbox.setHgrow()
            this.hbox.setHgrow(pane, Priority.ALWAYS);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);
            if (item != null) {
                label.setText(item);

                System.out.println(item);
                setGraphic(hbox);
            }
        }


}
