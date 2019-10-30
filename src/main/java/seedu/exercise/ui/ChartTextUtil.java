package seedu.exercise.ui;

/**
 * A class to format text in chart panels.
 */
public class ChartTextUtil {

    public static final String TITLE_FORMAT = "%s (%s to %s)";

    /**
     * Returns the formatted title of line chart and bar chart.
     */
    public static String lineAndBarChartTitleFormatter(String category, String startDate, String endDate) {
        return changeFirstLetterToUpperCase(String.format(TITLE_FORMAT, category, startDate, endDate));
    }

    /**
     * Returns the formatted title of pie chart.
     */
    public static String pieChartTitleFormatter(String category, String startDate, String endDate) {
        if (category.equals("exercise")) {
            return changeFirstLetterToUpperCase(String.format("%s Frequency (%s to %s)", category, startDate, endDate));
        } else {
            return changeFirstLetterToUpperCase(String.format(TITLE_FORMAT, category, startDate, endDate));
        }
    }

    /**
     * Returns the y-axis label of bar chart.
     */
    public static String barChartLabelFormatter(String category) {
        if (category.equals("exercise")) {
            return "Quantity";
        } else {
            return "kcal";
        }
    }

    /**
     * Returns the y-axis label of line chart.
     */
    public static String lineChartLabelFormatter(String category) {
        if (category.equals("exercise")) {
            return "Frequency";
        } else {
            return "kcal";
        }
    }

    /**
     * Returns the string with first letter changed to upper case.
     */
    public static String changeFirstLetterToUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
