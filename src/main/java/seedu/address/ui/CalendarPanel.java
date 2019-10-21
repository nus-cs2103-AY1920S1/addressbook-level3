package seedu.address.ui;

import java.awt.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * UI component that is displayed when the command to view calendar is issued.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday"};
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    private static final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int dayOfWeek;
    private int dayOfMonth;
    private int month;
    private int year;
    private Calendar calendar;
    private Image leftIcon = new Image(this.getClass().getResourceAsStream("/images/left_arrow.png"));
    private Image rightIcon = new Image(this.getClass().getResourceAsStream("/images/right_arrow.png"));


    @FXML
    private Label currDateMessage;

    @FXML
    private Label selectedDateMessage;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private GridPane calendarGridPane;

    public CalendarPanel() {
        super(FXML);
        calendar = Calendar.getInstance();
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        initialiseButtons();
        initialiseCurrentDate();
        initialiseSelectedDate(0);
    }

    private void initialiseCurrentDate() {
        String day = DAYS[dayOfWeek];
        String currMonth = MONTHS[month];
        currDateMessage.setText(day + ", " + currMonth + " " + dayOfMonth + ", " + year);
    }

    private void initialiseSelectedDate(int monthChange) {
        calendar.add(Calendar.MONTH, monthChange);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        String selectedMonth = MONTHS[month];
        selectedDateMessage.setText(selectedMonth + ", " + year);
        fillUpDays(year, month);
    }

    private void initialiseButtons() {
        prevButton.setGraphic(new ImageView(leftIcon));
        nextButton.setGraphic(new ImageView(rightIcon));
        prevButton.setText("");
        nextButton.setText("");
    }

    private void fillUpDays(int y, int m) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, 1);
        int leadGap = gregorianCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = DAYS_IN_MONTH[month];
        if (gregorianCalendar.isLeapYear(gregorianCalendar.get(Calendar.YEAR)) && month == 1) {
            daysInMonth++;
        }
        if (leadGap == 0) {
            leadGap = 7;
        }
        String[] before = previousMonth(month, leadGap);
        String[] current = currMonth(daysInMonth);

        int tailGap = 7 * 6 - (daysInMonth + leadGap);
        String[] after = nextMonth(month, tailGap);

        String[] combined = combine(before, current);
        combined = combine(combined, after);

        int beforeCount = before.length;
        int currentCount = current.length;
        int k = 0;
        for (int i = 0; i < 6; i++) {
            int row = i;
            for (int j = 0; j < 7; j++) {
                int column = j;
                HBox h = new HBox();
                Label l = new Label();
                l.setText(combined[k]);
                l.setPadding(new Insets(5,0,0,10));
                l.setFont(new Font("System", 11));
                l.setAlignment(Pos.TOP_LEFT);
                if (beforeCount > 0) {
                    l.setTextFill(Paint.valueOf("#999999"));
                    beforeCount--;
                } else if (currentCount > 0) {
                    Calendar c = Calendar.getInstance();
                    int yr = c.get(Calendar.YEAR);
                    int mt = c.get(Calendar.MONTH);
                    if (Integer.valueOf(combined[k]) == dayOfMonth && yr == y && mt == m) {
                        l.setTextFill(Paint.valueOf("#f64747"));
                        l.setStyle("-fx-font-weight:bold");
                    } else {
                        l.setTextFill(Paint.valueOf("#000000"));
                    }
                    currentCount--;
                } else {
                    l.setTextFill(Paint.valueOf("#999999"));
                }
                h.getChildren().addAll(l);
                calendarGridPane.add(h, column, row);
                k++;
            }
        }
    }

    private String[] previousMonth(int currMonth, int leadGap) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, currMonth);
        c.add(Calendar.MONTH, -1);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);

        GregorianCalendar gc = new GregorianCalendar(y, m, 1);
        int daysInMonth = DAYS_IN_MONTH[m];
        if (gc.isLeapYear(gc.get(Calendar.YEAR)) && m == 1) {
            daysInMonth++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = daysInMonth; i > 0; i--) {
            sb.append(i).append(" ");
        }
        String[] allDays = sb.toString().split("\\s");
        String[] neededDays = Arrays.copyOfRange(allDays, 0, leadGap);
        neededDays = reverse(neededDays);
        return neededDays;
    }

    private String[] reverse(String[] s) {
        for (int i = 0; i < (s.length - (i + 1)); i++) {
            String temp = s[s.length - (i + 1)];
            s[s.length - (i + 1)] = s[i];
            s[i] = temp;
        }

        return s;
    }

    private String[] currMonth(int numDays) {
        String[] result = new String[numDays];
        for (int i = 0; i < numDays; i++) {
            result[i] = Integer.toString(i + 1);
        }
        return result;
    }

    private String[] nextMonth(int currMonth, int tailGap) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, currMonth);
        c.add(Calendar.MONTH, 1);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);

        GregorianCalendar gc = new GregorianCalendar(y, m, 1);
        int daysInMonth = DAYS_IN_MONTH[m];
        if (gc.isLeapYear(gc.get(Calendar.YEAR)) && m == 1) {
            daysInMonth++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= daysInMonth; i++) {
            sb.append(i).append(" ");
        }
        String[] allDays = sb.toString().split("\\s");
        String[] neededDays = Arrays.copyOfRange(allDays, 0, tailGap);
        return neededDays;
    }

    private static String[] combine(String[] a, String[] b) {
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    @FXML
    private void handlePrevClick() {
        Node node = calendarGridPane.getChildren().get(0);
        calendarGridPane.getChildren().clear();
        calendarGridPane.getChildren().add(0, node);
        initialiseSelectedDate(-1);
    }

    @FXML
    private void handleNextClick() {
        Node node = calendarGridPane.getChildren().get(0);
        calendarGridPane.getChildren().clear();
        calendarGridPane.getChildren().add(0, node);
        initialiseSelectedDate(1);
    }
}
