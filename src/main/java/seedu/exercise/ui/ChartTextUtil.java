package seedu.exercise.ui;

/**
 * A class to format text in chart panels.
 */
public class ChartTextUtil {

    /**
     * Returns the formatted title of chart.
     */
    public static String titleFormatter(String category, String startDate, String endDate) {
        return category + " (" + startDate + " to " + endDate + ")";
    }

    /**
     * Returns the y-axis label of bar chart.
     */
    public static String barChartLabelFormatter(String category) {
        if (category.equals("exercise")) {
            return "quantity";
        } else {
            return "kcal";
        }
    }

    /**
     * Returns the y-axis label of line chart.
     */
    public static String lineChartLabelFormatter(String category) {
        if (category.equals("exercise")) {
            return "frequency";
        } else {
            return "kcal";
        }
    }
}
