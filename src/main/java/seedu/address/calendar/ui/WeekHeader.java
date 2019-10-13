package seedu.address.calendar.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.stream.IntStream;

class WeekHeader {
    private WeekHeader() {

    }

    static GridPane generateWeekHeader() {
        GridPane weekHeader = new GridPane();
        String[] days = { "Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat" };
        int widthPercent = 100 / days.length;
        weekHeader.setHgap(10);
        weekHeader.setVgap(10);
        weekHeader.setPadding(new Insets(10));
        IntStream.range(0, days.length)
                .forEach(i -> {
                    ColumnConstraints newColumn = new ColumnConstraints();
                    newColumn.setPercentWidth(widthPercent);
                    newColumn.setHalignment(HPos.CENTER);
                    weekHeader.getColumnConstraints().add(newColumn);
                    Label day = new Label(days[i]);
                    weekHeader.add(day, i, 0);
                });
        return weekHeader;
    }
}
