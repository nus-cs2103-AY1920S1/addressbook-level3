package seedu.address.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import seedu.address.model.entity.body.Body;

//@@author dalisc

/**
 * A ui for the line chart that is displayed at the bottom of the dashboard.
 */
public class LineChartPanel extends UiPart<Region> {

    private static LineChartPanel lineChartPanelInstance = null;
    private static final String FXML = "LineChartPanel.fxml";
    private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private static ObservableList<Body> staticBodyList;
    private static int windowSize = 10;
    private static String timeFrame = "default";
    private static Date date = new Date();

    // this is used to display time in HH:mm:ss format
    final SimpleDateFormat axisDateFormat = new SimpleDateFormat("EE, d/MM/yy");
    final SimpleDateFormat axisDateFormat2 = new SimpleDateFormat("d/MM/yy");
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/MM/yyyy");
    final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
    final NumberAxis yAxis = new NumberAxis();
    final AreaChart<String, Number> lineChart = new AreaChart<>(xAxis, yAxis);
    private XYChart.Series<String, Number> series;
    private Map<Date, Number> freqByDate = new TreeMap<>();
    private ObservableList<Body> bodyList;

    @FXML
    private TextArea resultDisplay;

    // Make LineChartPanel a singleton
    private LineChartPanel(ObservableList<Body> bodyList) throws ParseException {
        super(FXML);
        this.bodyList = bodyList;
        staticBodyList = bodyList;
    }

    // static method to create instance of Singleton class
    public static LineChartPanel getLineChartPanelInstance(ObservableList<Body> bodyList) throws ParseException {
        // To ensure only one instance is created
        if (lineChartPanelInstance == null) {
            lineChartPanelInstance = new LineChartPanel(bodyList);
        }
        return lineChartPanelInstance;
    }

    /**
     * Makes the line chart from scratch.
     * @throws ParseException if the date supplied by user is non-parsable.
     */
    private void makeLineChart() throws ParseException {
        initialiseTreeMap();
        initialiseLineChart();
        updateSeries();
        updateUponChange();
    }

    public AreaChart getLineChart() throws ParseException {
        makeLineChart();
        return lineChart;
    }

    /**
     * Initialises the line chart with a title, axes labels, and a series.
     */
    private void initialiseLineChart() {
        // clear old series
        lineChart.getData().removeAll(series);

        series = new XYChart.Series<>();

        //defining the axes
        if (timeFrame.equals("default") || timeFrame.equals("week")) {
            xAxis.setLabel("Day");
        } else if (timeFrame.equals("month")) {
            String month = new SimpleDateFormat("MMMM yyyy").format(date);
            xAxis.setLabel(month);
        } else if (timeFrame.equals("year")) {
            String year = new SimpleDateFormat("'Year' yyyy").format(date);
            xAxis.setLabel(year);
        }
        yAxis.setLabel("Number");
        // y axis shows only integers
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setMinorTickVisible(false);

        // creating the line chart with two axis created above
        lineChart.setTitle("Admission statistics");
        lineChart.setAnimated(false);

        // hide the legend because it takes up too much space
        lineChart.setLegendVisible(false);

        // add series to chart
        lineChart.getData().add(series);
    }

    /**
     * Updates the series whenever a body is removed or added.
     */
    private void updateUponChange() {
        bodyList.addListener((ListChangeListener<Body>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    try {
                        updateBodyAdded(c);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    updateSeries();
                }
                if (c.wasRemoved()) {
                    try {
                        updateBodyRemoved(c);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    updateSeries();
                }
            }
        });
    }

    /**
     * Initialises the tree map to include the latest 10 days.
     */
    private void initialiseTreeMap() throws ParseException {
        // Fill in the missing dates
        freqByDate.clear();
        Date ceilingDate;
        if (timeFrame.equals("week")) {
            List<Date> weekList = getWeekList(date);
            for (Date date: weekList) {
                Date noTimeDate = formatDateNoTime(date);
                freqByDate.putIfAbsent(noTimeDate, 0);
            }
            ceilingDate = weekList.get(6);
        } else if (timeFrame.equals("month")) {
            List<Date> monthList = getMonthList(date);
            for (Date date: monthList) {
                Date noTimeDate = formatDateNoTime(date);
                freqByDate.putIfAbsent(noTimeDate, 0);
            }
            ceilingDate = monthList.get(monthList.size() - 1);
        } else if (timeFrame.equals("year")) {
            List<Date> yearList = getYearList(date);
            for (Date date: yearList) {
                Date noTimeDate = formatDateNoTime(date);
                freqByDate.putIfAbsent(noTimeDate, 0);
            }
            ceilingDate = yearList.get(yearList.size() - 1);
        } else {
            Date now = new Date();
            Date tenDaysAgo = new Date(now.getTime() - (10 * DAY_IN_MS));
            for (Date date = now; date.after(tenDaysAgo); date = new Date(date.getTime() - DAY_IN_MS)) {
                Date noTimeDate = formatDateNoTime(date);
                freqByDate.putIfAbsent(noTimeDate, 0);
            }
            ceilingDate = now;
        }

        for (Body body: bodyList) {
            Date noTimeDate = formatDateNoTime(body.getDateOfAdmission());
            Number oldFreq = freqByDate.getOrDefault(noTimeDate, 0);
            int newFreq = oldFreq.intValue() + 1;
            if (noTimeDate.before(ceilingDate)) {
                freqByDate.put(noTimeDate, newFreq);
            }
        }

    }

    /**
     * Update the tree map upon a body being added - the frequency (value) associated with the date of admission (key)
     * will increase by one.
     * @param c Change in the ObservableList of bodies.
     */
    private void updateBodyAdded(ListChangeListener.Change<? extends Body> c) throws ParseException {
        Date date = c.getAddedSubList().get(0).getDateOfAdmission();
        Date noTimeDate = formatDateNoTime(date);
        Number oldFreq = freqByDate.getOrDefault(noTimeDate, 0);
        int newFreq = oldFreq.intValue() + 1;
        freqByDate.put(noTimeDate, newFreq);
    }

    /**
     * Update the tree map upon a body being deleted - the frequency (value) associated with the date of admission (key)
     * will decrease by one.
     * @param c Change in the ObservableList of bodies.
     */
    private void updateBodyRemoved(ListChangeListener.Change<? extends Body> c) throws ParseException {
        Date date = c.getRemoved().get(0).getDateOfAdmission();
        Date noTimeDate = formatDateNoTime(date);
        Number oldFreq = freqByDate.getOrDefault(noTimeDate, 0);
        int newFreq = oldFreq.intValue() - 1;
        freqByDate.put(noTimeDate, newFreq);
    }

    /**
     * Clears series data and populate series again with the current tree map keys and values.
     */
    private void updateSeries() {
        // clear previous data
        series.getData().clear();
        // Update series based on dates in current tree map
        freqByDate.forEach((date, freq) -> {
            if (timeFrame.equals("default") || timeFrame.equals("week")) {
                series.getData().add(new XYChart.Data<String, Number>(axisDateFormat.format(date), freq));
            } else if (timeFrame.equals("month") || timeFrame.equals("year")) {
                series.getData().add(new XYChart.Data<String, Number>(axisDateFormat2.format(date), freq));
            }
            if (series.getData().size() > windowSize) {
                series.getData().remove(0);
            }
        });
    }

    private Date formatDateNoTime(Date date) throws ParseException {
        return axisDateFormat.parse(axisDateFormat.format(date));
    }

    private List<Date> getWeekList(Date date) throws ParseException {
        List<Date> weekList = new ArrayList<>();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int dayOfWeekInt = dayOfWeek.getValue();
        int dayOfMonthInt = localDate.getDayOfMonth();
        int firstDayOfWeekInt = dayOfMonthInt - dayOfWeekInt + 1;
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        for (int i = firstDayOfWeekInt; i < firstDayOfWeekInt + 7; i++) {
            String dateString = String.format("%1$s/%2$s/%3$s", i, month, year);
            Date dateInMonth = simpleDateFormat.parse(dateString);
            weekList.add(dateInMonth);
        }
        return weekList;
    }

    private List<Date> getMonthList(Date date) throws ParseException {
        List<Date> monthList = new ArrayList<Date>();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthLength = localDate.lengthOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        for (int i = 1; i < monthLength + 1; i++) {
            String dateString = String.format("%1$s/%2$s/%3$s", i, month, year);
            Date dateInMonth = simpleDateFormat.parse(dateString);
            monthList.add(dateInMonth);
        }
        return monthList;
    }

    private List<Date> getYearList(Date date) throws ParseException {
        List<Date> yearList = new ArrayList<>();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        for (int i = 1; i < 13; i++) {
            String dateString = String.format("1/%1$s/%2$s", i, year);
            Date dateInMonth = simpleDateFormat.parse(dateString);
            List<Date> monthList = getMonthList(dateInMonth);
            monthList.forEach(d -> yearList.add(d));
        }
        return yearList;
    }

    /**
     * Sets the new filter parameters supplied by the user to change the time frame of the line chart.
     * @param newTimeFrame The time frame specified, which can be "default", "week", "month", or "year".
     * @param newDate The date specified by the user to contain the time frame.
     */
    public static void changeFilterParameters(String newTimeFrame, Date newDate) {
        setTimeFrame(newTimeFrame);
        setDate(newDate);
        setWindowSize(newTimeFrame);
    }

    private static void setTimeFrame(String newTimeFrame) {
        timeFrame = newTimeFrame;
    }

    private static void setDate(Date newDate) {
        date = newDate;
    }

    private static void setWindowSize(String timeFrame) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (timeFrame) {
        case "default":
            windowSize = 10;
            break;

        case "week":
            windowSize = 7;
            break;
        case "month":
            windowSize = localDate.lengthOfMonth();
            break;
        case "year":
            if (localDate.isLeapYear()) {
                windowSize = 366;
            } else {
                windowSize = 365;
            }
            break;
        default:
        }
    }

    public static void reinitialiseChart() throws ParseException {
        getLineChartPanelInstance(staticBodyList).makeLineChart();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LineChartPanel)) {
            return false;
        }

        // state check
        LineChartPanel otherLineChart = (LineChartPanel) other;
        return this.lineChart.equals(otherLineChart.lineChart);
    }
}
