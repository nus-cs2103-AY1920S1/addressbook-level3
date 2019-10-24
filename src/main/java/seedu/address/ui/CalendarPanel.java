package seedu.address.ui;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
        setButtonImage();
        calendar = Calendar.getInstance();
        retrieveCurrentDate();
        setCurrentDateTitle();
        initialiseSelectedDate(0);
    }

    private void setButtonImage() {
        prevButton.setGraphic(new ImageView(leftIcon));
        nextButton.setGraphic(new ImageView(rightIcon));
        prevButton.setText("");
        nextButton.setText("");
    }

    /**
     * Retrieve details of today's date.
     */
    private void retrieveCurrentDate() {
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    /**
     * Sets the title of the calendar feature to today's date.
     */
    private void setCurrentDateTitle() {
        String day = DAYS[dayOfWeek];
        String currMonth = MONTHS[month];
        currDateMessage.setText(day + ", " + currMonth + " " + dayOfMonth + ", " + year);
    }

    /**
     * Sets title of selected date and fills up the calendar grid with the appropriate days in
     * the month.
     * @param monthChange Represents difference in month from current month. e.g. -1
     *                    indicates previous month, 1 indicates the next month and 0 indicates
     *                    the current month
     */
    private void initialiseSelectedDate(int monthChange) {
        updateDateVariables(monthChange);
        setSelectedDateTitle();
        fillUpDays();
    }

    private void updateDateVariables(int monthChange) {
        calendar.add(Calendar.MONTH, monthChange);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
    }

    private void setSelectedDateTitle() {
        String selectedMonth = MONTHS[month];
        selectedDateMessage.setText(selectedMonth + ", " + year);
    }

    /**
     * Fills up calendar grid with days of selected month using provide year and month. Excess grid
     * boxes at start and end of calendar filled up with days from previous and next month with
     * respect to the selected month.
     */
    private void fillUpDays() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, 1);
        int leadGap = calculateLeadGap(gregorianCalendar);
        int daysInMonth = calculateDaysInMonth(gregorianCalendar);
        int tailGap = calculateTailGap(daysInMonth + leadGap);

        String[] before = previousMonth(month, leadGap);
        String[] current = currMonth(daysInMonth);
        String[] after = nextMonth(month, tailGap);

        String[] combined = combine(before, current, after);

        fillUpGrid(before.length, current.length, combined);
    }

    /**
     * Calculates excess grid boxes at the front of the calendar.
     * @param gc Calendar containing selected date
     * @return int Number of excess grid boxes.
     */
    private int calculateLeadGap(GregorianCalendar gc) {
        int result = gc.get(Calendar.DAY_OF_WEEK) - 1;
        if (result == 0) {
            result = 7;
        }
        return result;
    }

    /**
     * Calculates number of days in selected date.
     * @param gc Calendar containing selected date
     * @return int Number of days in selected date.
     */
    private int calculateDaysInMonth(GregorianCalendar gc) {
        int result = DAYS_IN_MONTH[month];
        if (gc.isLeapYear(gc.get(Calendar.YEAR)) && month == 1) {
            result++;
        }
        return result;
    }

    /**
     * Calculates excess grid boxes at the end of the calendar.
     * @param n Number of days of calendar already filled up
     * @return int Number of excess grid boxes.
     */
    private int calculateTailGap(int n) {
        return 42 - n;
    }

    /**
     * Generates array containing days from previous month.
     * @param currMonth Current selected month
     * @param leadGap Number of days needed from previous month
     * @return String array containing days from previous month.
     */
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

    /**
     * Reverses a String array.
     * @param s String array to be reversed
     * @return Reversed String array.
     */
    private String[] reverse(String[] s) {
        for (int i = 0; i < (s.length - (i + 1)); i++) {
            String temp = s[s.length - (i + 1)];
            s[s.length - (i + 1)] = s[i];
            s[i] = temp;
        }

        return s;
    }

    /**
     * Generates array containing days from selected month.
     * @param numDays Number of days in selected month
     * @return String array containing days from selected month.
     */
    private String[] currMonth(int numDays) {
        String[] result = new String[numDays];
        for (int i = 0; i < numDays; i++) {
            result[i] = Integer.toString(i + 1);
        }
        return result;
    }

    /**
     * Generates array containing days from next month.
     * @param currMonth Current selected month
     * @param tailGap Number of days needed from next month
     * @return String array containing days from next month.
     */
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

    /**
     * Combines 3 string arrays together, with array {@code a}'s elements coming in front, array
     * {@code b}'s elements coming in the middle and array {@code c}'s elements coming at the back.
     * @param a String array whose elements are to be in front
     * @param b String array whose elements are to be in the middle
     * @param c String array whose elements are to be behind
     * @return String[] Combined String array.
     */
    private String[] combine(String[] a, String[] b, String[] c) {
        int length = a.length + b.length + c.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        System.arraycopy(c, 0, result, a.length + b.length, c.length);
        return result;
    }

    /**
     * Creates a label to be placed inside each grid of the grid pane in the FXML.
     * @param numBefore Number of days from month before
     * @param numCurr Number of days from selected month
     * @param days String array containing all 42 days to be used to fill up the 7 * 6 grid pane
     */
    private void fillUpGrid(int numBefore, int numCurr, String[] days) {
        int beforeCount = numBefore;
        int currentCount = numCurr;
        int counter = 0;
        for (int i = 0; i < 6; i++) {
            int row = i;
            for (int j = 0; j < 7; j++) {
                int column = j;
                HBox h = new HBox();
                Label l = new Label();
                l.setText(days[counter]);
                l.setPadding(new Insets(5, 0, 0, 10));
                l.setFont(new Font("System", 11));
                l.setAlignment(Pos.TOP_LEFT);
                if (beforeCount > 0) {
                    l.setTextFill(Paint.valueOf("#999999"));
                    beforeCount--;
                } else if (currentCount > 0) {
                    Calendar c = Calendar.getInstance();
                    int yr = c.get(Calendar.YEAR);
                    int mt = c.get(Calendar.MONTH);
                    // mark today's date in red and bold, otherwise mark as normal
                    if (Integer.valueOf(days[counter]) == dayOfMonth && yr == year && mt == month) {
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
                counter++;
            }
        }
    }

    /**
     * Initialise calendar to previous month data when the previous button is clicked.
     */
    @FXML
    private void handlePrevClick() {
        Node node = calendarGridPane.getChildren().get(0);
        calendarGridPane.getChildren().clear();
        calendarGridPane.getChildren().add(0, node);
        initialiseSelectedDate(-1);
    }

    /**
     * Initialise calendar to previous month data when the next button is clicked.
     */
    @FXML
    private void handleNextClick() {
        Node node = calendarGridPane.getChildren().get(0);
        calendarGridPane.getChildren().clear();
        calendarGridPane.getChildren().add(0, node);
        initialiseSelectedDate(1);
    }
}
