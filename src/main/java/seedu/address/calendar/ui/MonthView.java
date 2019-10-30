package seedu.address.calendar.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import seedu.address.calendar.model.date.ViewOnlyDay;
import seedu.address.calendar.model.date.ViewOnlyMonth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class MonthView {
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 7;
    private ViewOnlyMonth viewOnlyMonth;
    private ReadOnlyDoubleProperty monthViewWidth;
    private static List<DayView> dayViews;
    private static MonthView monthView;

    // todo: make constructor private, set monthViewWidth as static and have setMonthViewWidth --> defensive
    private MonthView(ViewOnlyMonth viewOnlyMonth, ReadOnlyDoubleProperty monthViewWidth) {
        removeListeners();
        dayViews = new ArrayList<>();
        this.viewOnlyMonth = viewOnlyMonth;
        this.monthViewWidth = monthViewWidth;
    }

    static GridPane generateMonthGrid(ViewOnlyMonth viewOnlyMonth, ReadOnlyDoubleProperty monthViewWidth) {
        monthView = new MonthView(viewOnlyMonth, monthViewWidth);

        Stream<ViewOnlyDay> days = viewOnlyMonth.getDaysInMonth();
        ViewOnlyDay firstDay = viewOnlyMonth.getFirstDayOfMonth();

        GridPane monthView = new GridPane();
        GridPane.setVgrow(monthView, Priority.ALWAYS);
        GridPane.setHgrow(monthView, Priority.ALWAYS);

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

        days.forEach(day -> {
            boolean hasCommitment = day.hasCommitment();
            boolean hasHoliday = day.hasHoliday();
            boolean hasSchoolBreak = day.hasSchoolBreak();
            boolean hasTrip = day.hasTrip();
            DayView dayView = new DayView(day.getDayOfMonth(), monthViewWidth, hasCommitment, hasHoliday, hasSchoolBreak, hasTrip);
            dayViews.add(dayView);
            int firstDayOfWeekAsNum = firstDay.getDayOfWeekZeroIndex() - 1;
            int rowIndex = (firstDayOfWeekAsNum + day.getDayOfMonth()) / 7;
            int shiftedRowIndex = rowIndex < 5 ? rowIndex : 0;
            monthView.add(dayView.getRoot(), day.getDayOfWeekZeroIndex(), shiftedRowIndex);
        });

        return monthView;
    }

    public void removeListeners() {
        if (dayViews == null) {
            return;
        }
        dayViews.forEach(dayView -> dayView.removeListener());
    }
}
