package seedu.address.calendar.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import seedu.address.calendar.model.Day;
import seedu.address.calendar.model.Month;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class MonthView {
    private Month month;

    MonthView(Month month) {
        this.month = month;
    }

    GridPane generateMonthGrid() {
        Stream<Day> days = month.getDaysInMonth();
        Day firstDay = month.getFirstDayOfMonth();

        int height = 100;
        GridPane monthView = new GridPane();
        monthView.setGridLinesVisible(true);
        IntStream.range(0, 5)
                .forEach(i -> {
                    RowConstraints newRow = new RowConstraints();
                    newRow.setPrefHeight(height);
                    monthView.getRowConstraints().add(newRow);
                });
        int width = 150;

        days.forEach(day -> {
            VBox dayView = generateDayView(day.getDayOfMonth());
            dayView.setPrefWidth(width);
            int firstDayOfWeekAsNum = firstDay.getDayOfWeekZeroIndex();
            int rowIndex = (firstDayOfWeekAsNum + day.getDayOfMonth()) / 7;
            int shiftedRowIndex = rowIndex < 5 ? rowIndex : 0;
            monthView.add(dayView, day.getDayOfWeekOneBased(), shiftedRowIndex);
        });
        return monthView;
    }

    Label generateMonthLabel() {
        String unformattedMonthLabel = month.getMonthOfYear().toString();
        String formattedMonthLabel = unformattedMonthLabel.charAt(0) + unformattedMonthLabel.substring(1).toLowerCase();
        return new Label(formattedMonthLabel);
    }

    // todo change this to a class which has a setDate method
    private VBox generateDayView(int date) {
        VBox dayView = new VBox();
        Label dateOfMonth = new Label(Integer.toString(date));
        dayView.getChildren().add(dateOfMonth);
        dayView.setPadding(new Insets(10));
        return dayView;
    }
}
