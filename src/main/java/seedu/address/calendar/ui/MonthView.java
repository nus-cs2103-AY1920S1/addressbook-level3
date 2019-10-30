package seedu.address.calendar.ui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import seedu.address.calendar.model.date.ViewOnlyDay;
import seedu.address.calendar.model.date.ViewOnlyMonth;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class MonthView {
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 7;
    private ViewOnlyMonth viewOnlyMonth;

    MonthView(ViewOnlyMonth viewOnlyMonth) {
        this.viewOnlyMonth = viewOnlyMonth;
    }

    GridPane generateMonthGrid() {
        Stream<ViewOnlyDay> days = viewOnlyMonth.getDaysInMonth();
        ViewOnlyDay firstDay = viewOnlyMonth.getFirstDayOfMonth();

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
            int firstDayOfWeekAsNum = firstDay.getDayOfWeekZeroIndex() - 1;
            int rowIndex = (firstDayOfWeekAsNum + day.getDayOfMonth()) / 7;
            int shiftedRowIndex = rowIndex < 5 ? rowIndex : 0;
            monthView.add(dayView.getRoot(), day.getDayOfWeekZeroIndex(), shiftedRowIndex);
        });

        return monthView;
    }
}
