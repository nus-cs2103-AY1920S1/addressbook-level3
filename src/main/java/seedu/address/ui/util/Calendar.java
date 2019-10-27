package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * A class to show the calendar view in default home page.
 */
public class Calendar extends UiPart<Region> {
    private static String FXML = "Calendar.fxml";
    private LocalDate today;

    @FXML
    private GridPane calendar;

    @FXML
    private Label dateHeader;

    @FXML
    private VBox calendarContainer;

    private LocalDate[] dateArray;

    public Calendar(LocalDate today) {
        super(FXML);
        this.today = today;
        this.dateArray = new LocalDate[42];
        String header = today.getMonth().toString();
        dateHeader.setText(header);
        initialiseCalendarHeader();
        fillInDateArray();
        fillInDates();
    }

    public void initialiseCalendarHeader() {
        for (int i = 1; i <= 7; i++) {
            String day = DayOfWeek.of(i).toString().substring(0, 3);
            Label dayLabel = new Label(day);
            calendar.add(wrapLabelInStackPane(dayLabel), i - 1, 0);
        }
    }

    public void fillInDateArray() {
        Month month = today.getMonth();
        LocalDate startingDate = LocalDate.of(today.getYear(), month, 1);
        int offset = startingDate.getDayOfWeek().getValue() - 1;
        int maxDaysForThisMonth = month.maxLength();
        //Fill in the before
        for (int i = 0; i < offset; i++) {
            dateArray[i] = startingDate.minusDays(offset - i);
        }
        //Fill in the after
        for (int j = 0; j < 42 - offset; j++) {
            dateArray[offset + j] = startingDate.plusDays(j);
        }
    }

    public void fillInDates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                LocalDate date = dateArray[i + j * 7];
                Label dateNumber = new Label(date.getDayOfMonth() + "");
                dateNumber.setId("dateNumber");
                if (date.getMonthValue() != today.getMonthValue()) {
                    dateNumber.setStyle("-fx-opacity: 0.3;");
                }
                StackPane dateWrapper = wrapLabelInStackPane(dateNumber);
                calendar.add(dateWrapper, i, j + 1);
                if (date.equals(today)) {
                    dateWrapper.setId("dateNumberSelected");
                }
            }
        }
    }

    private StackPane wrapLabelInStackPane(Label label) {
        Region region = new Region();
        StackPane stackPane = new StackPane();
        region.setPrefSize(50, 50);
        label.setPrefSize(50, 50);
        label.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(region, label);
        return stackPane;
    }
}
