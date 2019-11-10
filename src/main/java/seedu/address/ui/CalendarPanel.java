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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;

/**
 * UI component that is displayed when the command to view calendar is issued.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final String[] DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu",
        "Fri", "Sat"};
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};
    private static final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int dayOfWeek;
    private int dayOfMonth;
    private int month;
    private int year;
    private Calendar calendar;
    private Model model;
    private Image leftArrowIcon = new Image(this.getClass().getResourceAsStream("/images/left_arrow.png"));
    private Image rightArrowIcon = new Image(this.getClass().getResourceAsStream("/images/right_arrow.png"));
    private Image trainingIcon = new Image(this.getClass().getResourceAsStream("/images/green_dot.png"));
    private Image performanceIcon = new Image(this.getClass().getResourceAsStream("/images/purple_dot.png"));

    @FXML
    private Label currYear;

    @FXML
    private Label currDayAndDate;

    @FXML
    private Label selectedDateMessage;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private GridPane calendarGridPane;

    /**
     * Class constructor that constructs calendar for current month when no date is provided.
     * @param model Represents memory of Athletick
     */
    public CalendarPanel(Model model) {
        super(FXML);
        this.model = model;
        setButtonImage();
        calendar = Calendar.getInstance();
        retrieveCurrentDate();
        setCurrentDateTitle();
        initialiseSelectedDate(0);
    }

    /**
     * Class constructor that constructs calendar for provided {@code date}.
     * @param date Date
     * @param model Represents memory of Athletick
     */
    public CalendarPanel(AthletickDate date, Model model) {
        super(FXML);
        this.model = model;
        setButtonImage();
        calendar = Calendar.getInstance();
        retrieveCurrentDate();
        setCurrentDateTitle();
        retrieveProvidedDate(date);
        initialiseSelectedDate(0);
    }

    /**
     * Sets image of buttons to left and right arrows to navigate calendar.
     */
    private void setButtonImage() {
        ImageView leftArrow = createButtonImage(leftArrowIcon);
        ImageView rightArrow = createButtonImage(rightArrowIcon);
        prevButton.setGraphic(leftArrow);
        nextButton.setGraphic(rightArrow);
        prevButton.setText("");
        nextButton.setText("");
    }

    /**
     * Creates image for button in desired dimensions.
     * @param image Image to be used
     * @return ImageView in desired dimensions
     */
    private ImageView createButtonImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        return imageView;
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
        String day = DAYS[dayOfWeek - 1];
        String currMonth = MONTHS[month];
        currYear.setText(Integer.toString(year));
        currDayAndDate.setText(day + ", " + currMonth + " " + dayOfMonth);
    }

    /**
     * Assigns month and year using the provided {@code date}.
     * @param date Date
     */
    private void retrieveProvidedDate(AthletickDate date) {
        month = date.getMonth() - 1;
        year = date.getYear();
        calendar.set(year, month, 1);
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

    /**
     * Updates month and year variables based on the provided monthChange.
     * @param monthChange Represents difference in month from current month. e.g. -1
     *                    indicates previous month, 1 indicates the next month and 0 indicates
     *                    the current month
     */
    private void updateDateVariables(int monthChange) {
        calendar.add(Calendar.MONTH, monthChange);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
    }

    /**
     * Sets the text of the selected date label to display to the user.
     */
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
     * @param currFilledGrids Number of days of calendar already filled up
     * @return int Number of excess grid boxes.
     */
    private int calculateTailGap(int currFilledGrids) {
        return 42 - currFilledGrids;
    }

    /**
     * Generates array containing days from previous month.
     * @param currMonth Current selected month
     * @param leadGap Number of days needed from previous month
     * @return String array containing days from previous month.
     */
    private String[] previousMonth(int currMonth, int leadGap) {
        Calendar c = calendarConstructor(currMonth, -1);
        int daysInMonth = checkLeapYear(c);

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
        Calendar c = calendarConstructor(currMonth, 1);
        int daysInMonth = checkLeapYear(c);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= daysInMonth; i++) {
            sb.append(i).append(" ");
        }
        String[] allDays = sb.toString().split("\\s");
        String[] neededDays = Arrays.copyOfRange(allDays, 0, tailGap);
        return neededDays;
    }

    /**
     * Constructs a calendar with the given month. Year will be updated as well if required.
     * @param currMonth Current month
     * @param monthChange Represents difference in month from current month. e.g. -1
     *                    indicates previous month, 1 indicates the next month and 0 indicates
     *                    the current month
     * @return Calendar with desired month and year.
     */
    private Calendar calendarConstructor(int currMonth, int monthChange) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, currMonth);
        c.add(Calendar.MONTH, monthChange);
        return c;
    }

    /**
     * Checks if the year is a leap year and increments the number of days in month if leap year
     * and month is February.
     * @param c Calendar with month and year
     * @return Number of days in month
     */
    private int checkLeapYear(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int daysInMonth = DAYS_IN_MONTH[month];
        if (gc.isLeapYear(gc.get(Calendar.YEAR)) && month == 1) {
            daysInMonth++;
        }
        return daysInMonth;
    }

    /**
     * Combines 3 string arrays together, with array {@code a}'s elements coming in front, array
     * {@code b}'s elements coming in the middle and array {@code c}'s elements coming at the back.
     * @param prev String array whose elements are to be in front
     * @param curr String array whose elements are to be in the middle
     * @param next String array whose elements are to be behind
     * @return String[] Combined String array.
     */
    private String[] combine(String[] prev, String[] curr, String[] next) {
        int length = prev.length + curr.length + next.length;
        String[] result = new String[length];
        System.arraycopy(prev, 0, result, 0, prev.length);
        System.arraycopy(curr, 0, result, prev.length, curr.length);
        System.arraycopy(next, 0, result, prev.length + curr.length, next.length);
        return result;
    }

    /**
     * Adds date numbers and dot indicators inside each grid of the 42 cell grid pane.
     * @param numBefore Number of days from month before
     * @param numCurr Number of days from selected month
     * @param days String array containing all 42 days to be used to fill up the 7 * 6 grid pane
     */
    private void fillUpGrid(int numBefore, int numCurr, String[] days) {
        int beforeCount = numBefore;
        int currentCount = numCurr;
        int counter = 0;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                String day = days[counter];
                Label l = createLabel(day);
                boolean havePerformanceEntry;
                boolean haveTrainingEntry;
                boolean setColour = false;
                if (beforeCount > 0) {
                    // fills up leading days from previous month
                    l.setTextFill(Paint.valueOf("#999999"));
                    havePerformanceEntry = checkPerformanceEntryExists(day, -1);
                    haveTrainingEntry = checkTrainingEntryExists(day, -1);
                    beforeCount--;
                } else if (currentCount > 0) {
                    // fills up days for current month
                    // marks today's date in a blue circle and bold white text
                    if (isToday(days[counter])) {
                        l.setTextFill(Paint.valueOf("#ffffff"));
                        l.setStyle("-fx-font-weight:bold");
                        // setColour results in a blue circle around the number and indicators
                        setColour = true;
                    }
                    havePerformanceEntry = checkPerformanceEntryExists(day, 0);
                    haveTrainingEntry = checkTrainingEntryExists(day, 0);
                    currentCount--;
                } else {
                    // fills up days for next month
                    havePerformanceEntry = checkPerformanceEntryExists(day, 1);
                    haveTrainingEntry = checkTrainingEntryExists(day, 1);
                    l.setTextFill(Paint.valueOf("#999999"));
                }
                addContentToGrid(col, row, l, haveTrainingEntry, havePerformanceEntry, setColour);
                counter++;
            }
        }
    }

    /**
     * Adds label to the provided coordinates of the grid.
     * @param col Column
     * @param row Row
     * @param l Label
     * @param haveTrainingEntry Indicates if there is a training entry on the date
     * @param havePerformanceEntry Indicates if there is a performance entry on the date
     * @param setColour Indicates if the date corresponds to today's date
     */
    private void addContentToGrid(int col, int row, Label l, boolean haveTrainingEntry,
                                  boolean havePerformanceEntry, boolean setColour) {
        ImageView performanceIndicator = createPerformanceIndicator();
        ImageView trainingIndicator = createTrainingIndicator();
        if (havePerformanceEntry && haveTrainingEntry) {
            calendarGridPane.add(gridContent(setColour, l,
                    combineIndicators(trainingIndicator, performanceIndicator)), col, row);
        } else if (havePerformanceEntry) {
            calendarGridPane.add(gridContent(setColour, l, performanceIndicator), col, row);
        } else if (haveTrainingEntry) {
            calendarGridPane.add(gridContent(setColour, l, trainingIndicator), col, row);
        } else {
            calendarGridPane.add(gridContent(setColour, l), col, row);
        }
    }

    /**
     * Checks if there is a performance entry on a particular AthletickDate constructed using the
     * visible calendar dates.
     * @param day Day
     * @param monthChange Represents difference in month from current month. e.g. -1
     *                    indicates previous month, 1 indicates the next month and 0 indicates
     *                    the current month
     * @return boolean True if there is a performance entry on that date, false otherwise.
     */
    private boolean checkPerformanceEntryExists(String day, int monthChange) {
        AthletickDate ad = dateConstructor(day, monthChange);
        return model.hasPerformanceOn(ad);
    }

    /**
     * Creates a performance dot indicator.
     * @return ImageView with set image and desired dimensions.
     */
    private ImageView createPerformanceIndicator() {
        ImageView i = new ImageView(performanceIcon);
        i.setFitHeight(6);
        i.setFitWidth(6);
        return i;
    }

    /**
     * Checks if there is a training entry on a particular AthletickDate constructed using the
     * visible calendar dates.
     * @param day Day
     * @param monthChange Represents difference in month from current month. e.g. -1
     *                    indicates previous month, 1 indicates the next month and 0 indicates
     *                    the current month
     * @return boolean True if there is a training entry on that date, false otherwise.
     */
    private boolean checkTrainingEntryExists(String day, int monthChange) {
        AthletickDate ad = dateConstructor(day, monthChange);
        return model.hasTrainingOn(ad);
    }

    /**
     * Constructs AthletickDate using provided day and monthChange for methods
     * checkTrainingEntryExists() and checkPerformanceEntryExists().
     * @param day Day
     * @param monthChange Represents difference in month from current month. e.g. -1
     *                    indicates previous month, 1 indicates the next month and 0 indicates
     *                    the current month
     * @return AthletickDate with desired day, month and year.
     */
    private AthletickDate dateConstructor(String day, int monthChange) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.add(Calendar.MONTH, monthChange);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        AthletickDate ad = new AthletickDate(Integer.parseInt(day), m + 1, y, 1,
                MONTHS[m]);
        return ad;
    }

    /**
     * Creates a training dot indicator.
     * @return ImageView with set image and desired dimensions.
     */
    private ImageView createTrainingIndicator() {
        ImageView i = new ImageView(trainingIcon);
        i.setFitHeight(6);
        i.setFitWidth(6);
        return i;
    }

    /**
     * Creates a label with the provided {@code labelText}.
     * @param labelText String to be used as text
     * @return Label with text set as {@code labelText}.
     */
    private Label createLabel(String labelText) {
        Label l = new Label();
        l.setText(labelText);
        l.setPadding(new Insets(5, 0, 0, 0));
        l.setFont(new Font("System", 11));
        l.setTextFill(Paint.valueOf("#000000"));
        return l;
    }

    /**
     * Checks if date on calendar corresponds with today's date.
     * @param day Day
     * @return True if date is today's date, false otherwise.
     */
    private boolean isToday(String day) {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        return (Integer.parseInt(day) == dayOfMonth && y == year && m == month);
    }

    /**
     * Wraps {@code items} inside a {@code VBox} and returns it.
     * @param items Items to be wrapped
     * @return VBox containing item.
     */
    private VBox gridContent(boolean setColor, Node ... items) {
        VBox v = createGridContentHolder();
        for (Node item : items) {
            v.getChildren().add(item);
        }
        if (setColor) {
            v.setStyle("-fx-background-color:  #30336B; -fx-background-radius: 180");
        }
        return v;
    }

    /**
     * Creates {@code VBox} with desired settings that will contain all items for each
     * individual grid.
     * @return VBox with desired settings.
     */
    private VBox createGridContentHolder() {
        VBox v = new VBox();
        v.setFillWidth(false);
        v.setPrefHeight(30);
        v.setPrefHeight(30);
        v.setMaxSize(30, 30);
        v.setAlignment(Pos.TOP_CENTER);
        return v;
    }

    /**
     * Combines training and performance indicators together when a date has both training and
     * performance records.
     * @param items Dot indicators
     * @return HBox with indicators placed inside with appropriate margins between indicators.
     */
    private HBox combineIndicators(Node ... items) {
        HBox h = createCombineIndicatorHolder();
        for (Node item : items) {
            VBox v = createIndicatorHolder();
            v.getChildren().add(item);
            h.getChildren().add(v);
        }
        return h;
    }

    /**
     * Creates a {@code HBox} with the desired settings to contain the 2 indicators beside each
     * other.
     * @return HBox with desired settings
     */
    private HBox createCombineIndicatorHolder() {
        HBox h = new HBox();
        h.setFillHeight(false);
        h.setPrefHeight(Region.USE_COMPUTED_SIZE);
        h.setPrefWidth(Region.USE_COMPUTED_SIZE);
        h.setAlignment(Pos.TOP_CENTER);
        return h;
    }

    /**
     * Creates a {@VBox} with the desired settings to contain a single indicator.
     * @return VBox with desired settings
     */
    private VBox createIndicatorHolder() {
        VBox v = new VBox();
        v.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        v.setPadding(new Insets(0, 3, 0, 3));
        return v;
    }

    /**
     * Initialise calendar to previous month data when the previous button is clicked.
     */
    @FXML
    private void handlePrevClick() {
        calendarGridPane.getChildren().clear();
        initialiseSelectedDate(-1);
    }

    /**
     * Initialise calendar to previous month data when the next button is clicked.
     */
    @FXML
    private void handleNextClick() {
        calendarGridPane.getChildren().clear();
        initialiseSelectedDate(1);
    }
}
