package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.ui.UiPart;

public class MonthHeader extends UiPart<Region> {
    private static final String FXML = "CalendarMonthHeader.fxml";

    private static final int MONTH_STR_LENGTH = 3;

    @FXML
    private Label monthHeader;

    public MonthHeader(MonthOfYear monthOfYear) {
        super(FXML);
        setMonthHeader(monthOfYear);
    }

    void setMonthHeader(MonthOfYear monthOfYear) {
        String formattedMonthStr = formatMonth(monthOfYear);
        monthHeader.setText(formattedMonthStr);
    }

    private String formatMonth(MonthOfYear monthOfYear) {
        String month = monthOfYear.toString();
        String formattedMonthStr = month.substring(0, 1).toUpperCase()
                + month.substring(1, MONTH_STR_LENGTH).toLowerCase();
        return formattedMonthStr;
    }
}
