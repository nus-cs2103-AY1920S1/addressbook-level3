package seedu.address.calendar.ui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import seedu.address.calendar.model.Day;
import seedu.address.calendar.model.Month;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class MonthView {
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 7;
    private Month month;

    MonthView(Month month) {
        this.month = month;
    }

    GridPane generateMonthGrid() {
        Stream<Day> days = month.getDaysInMonth();
        Day firstDay = month.getFirstDayOfMonth();

        GridPane monthView = new GridPane();
        GridPane.setVgrow(monthView, Priority.ALWAYS);
        GridPane.setHgrow(monthView, Priority.ALWAYS);
        monthView.setGridLinesVisible(true);
        IntStream.range(0, NUM_ROWS)
                .forEach(i -> {
                    RowConstraints newRow = new RowConstraints();
                    newRow.setValignment(VPos.TOP);
                    newRow.setVgrow(Priority.ALWAYS);
                    monthView.getRowConstraints().add(newRow);
                });

        IntStream.range(0, NUM_COLS)
                .forEach(i -> {
                    ColumnConstraints col = new ColumnConstraints();
                    col.setHgrow(Priority.ALWAYS);
                    col.setHalignment(HPos.CENTER);
                    monthView.getColumnConstraints().add(col);
                });

        monthView.setGridLinesVisible(false);

        days.forEach(day -> {
            DayView dayView = new DayView(day.getDayOfMonth());
            int firstDayOfWeekAsNum = firstDay.getDayOfWeekZeroIndex();
            int rowIndex = (firstDayOfWeekAsNum + day.getDayOfMonth()) / 7;
            int shiftedRowIndex = rowIndex < 5 ? rowIndex : 0;
            monthView.add(dayView.getRoot(), day.getDayOfWeekOneBased(), shiftedRowIndex);
        });

        return monthView;
    }

    Label generateMonthLabel() {
        String unformattedMonthLabel = month.getMonthOfYear().toString();
        String formattedMonthLabel = unformattedMonthLabel.charAt(0)
                + unformattedMonthLabel.substring(1).toLowerCase();
        return new Label(formattedMonthLabel);
    }
}
