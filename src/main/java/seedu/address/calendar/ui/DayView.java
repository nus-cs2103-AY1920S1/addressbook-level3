package seedu.address.calendar.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DayView {

    static VBox generateDayView(int date) {
        VBox dayView = new VBox();
        Label dateOfMonth = new Label(Integer.toString(date));
        dayView.getChildren().add(dateOfMonth);
        dayView.setPadding(new Insets(10));
        return dayView;
    }
}
