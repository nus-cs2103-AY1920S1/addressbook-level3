package seedu.exercise.ui;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

/**
 * A class that contains common methods for ChartPanel.
 */
public class ChartUtil {

    public static final String TITLE_FORMAT = "%s (%s to %s)";

    /**
     * Install tooltip for bar chart and line chart.
     */
    public static void installToolTipXyChart(ObservableList<XYChart.Data<String, Double>> data) {
        data.stream().forEach(d -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(d.getXValue() + "\n" + d.getYValue());
            Tooltip.install(d.getNode(), tooltip);
        });
    }

    /**
     * Install tooltip for pie chart.
     */
    public static void installToolTipPieChart(ObservableList<PieChart.Data> data) {
        data.stream().forEach(d -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(d.getName());
            Tooltip.install(d.getNode(), tooltip);
        });
    }

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
     * Returns the y-axis label of bar and line chart.
     */
    public static String labelFormatter(String category) {
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

    /**
     * If the string is too long, it will format string to first 10 character plus last 8 character.
     */
    public static String propertyFormatter(String string) {
        string = changeFirstLetterToUpperCase(string);
        int length = string.length();
        if (length > 18) {
            string = string.substring(0, 10) + "..." + string.substring(length - 8);
        }

        return string;
    }

    /**
     * Returns the percentage of property for pie chart.
     */
    public static String percentageFormatter(String property, double percentage) {
        return String.format("%s \n[%.2f%%]", property, percentage);
    }

    /**
     * Returns the formatted string of total.
     */
    public static String totalFormatter(String category, double total) {
        String totalString = numberFormatter(total);
        if (category.equals("exercise")) {
            return String.format("Total: %s Exercise(s)", totalString);
        } else {
            return String.format("Total: %s kcal", totalString);
        }
    }

    /**
     * Returns the formatted string of average.
     */
    public static String averageFormatter(String category, double average) {
        String averageString = numberFormatter(average);
        if (category.equals("exercise")) {
            return String.format("Average: %s Exercise(s)", averageString);
        } else {
            return String.format("Average: %s kcal", averageString);
        }
    }

    /**
     * Change number to format of million, billion, trillion or quadrillion.
     */
    public static String numberFormatter(double number) {
        long million = 1000000L;
        long billion = 1000000000L;
        long trillion = 1000000000000L;
        long quadrillion = 1000000000000000L;

        if (number > quadrillion) {
            return String.format("%.2f quadrillion", number / quadrillion);
        }

        if (number > trillion) {
            return String.format("%.2f trillion", number / trillion);
        }

        if (number > billion) {
            return String.format("%.2f billion", number / billion);
        }

        if (number > million) {
            return String.format("%.2f million", number / million);
        }

        return String.format("%.2f", number);
    }
}
