package seedu.ifridge.model.waste;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

import org.jfree.data.time.Month;

import seedu.ifridge.model.waste.exceptions.WasteMonthException;

/**
 * The Waste Month to index the list of waste lists.
 */
public class WasteMonth implements Comparable<WasteMonth> {

    public static final String MESSAGE_CONSTRAINTS =
            "The month given is invalid. \nTo avoid ambiguity, we advice you to specify the month in a relaxed date "
                    + "format, e.g. Sep 2019";
    public static final Pattern VALIDATION_REGEX = Pattern.compile("^(0[1-9]|1[012])-((19|2[0-9])[0-9]{2})$");

    private final int month;
    private final int year;

    public WasteMonth(int month, int year) {
        boolean isValidMonth = month > 0 && month < 13;
        boolean isValidYear = year > 1900 && year < 5000;
        if (!isValidMonth || !isValidYear) {
            String errorMessage = isValidMonth ? "Month is valid, " : "Month is not valid, ";
            errorMessage += isValidYear ? "Year is valid." : "Year is not valid.";
            throw new WasteMonthException(errorMessage);
        }
        this.month = month;
        this.year = year;
    }

    public WasteMonth(LocalDate date) {
        requireNonNull(date);
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public WasteMonth(String wasteMonthString) {
        requireNonNull(wasteMonthString);
        if (!VALIDATION_REGEX.matcher(wasteMonthString).matches()) {
            throw new WasteMonthException("Invalid Format, storage waste month must be of format MM-yyyy");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        YearMonth ym = YearMonth.parse(wasteMonthString, formatter);
        this.month = ym.getMonthValue();
        this.year = ym.getYear();
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isAfter(WasteMonth otherWasteMonth) {
        return this.compareTo(otherWasteMonth) > 0;
    }

    public boolean isBefore(WasteMonth otherWasteMonth) {
        return this.compareTo(otherWasteMonth) < 0;
    }

    /**
     * Creates a new WasteMonth object for the next month and returns it.
     */
    public WasteMonth nextWasteMonth() {
        return addWasteMonth(1);
    }

    /**
     * Adds the specified number of months from the current waste month and returns a new waste month object.
     */
    public WasteMonth addWasteMonth(int numberOfMonths) {
        LocalDate currentWasteMonthDate = LocalDate.of(this.year, this.month, 1);
        return new WasteMonth(currentWasteMonthDate.plusMonths(numberOfMonths));
    }

    public WasteMonth previousWasteMonth() {
        return minusWasteMonth(1);
    }

    /**
     * Subtracts the specified number of months from the current waste month and returns a new waste month object.
     */
    public WasteMonth minusWasteMonth(int numberOfMonths) {
        LocalDate currentWasteMonthDate = LocalDate.of(this.year, this.month, 1);
        return new WasteMonth(currentWasteMonthDate.minusMonths(numberOfMonths));
    }

    /**
     * Returns the format which the waste month will be stored in storage.
     */
    public String toStorageFormat() {
        String month = (this.month < 10) ? "0" + String.valueOf(this.month) : String.valueOf(this.month);
        String year = String.valueOf(this.year);
        return month + "-" + year;
    }

    /**
     * Converts the WasteMonth object into a JFreeMonth object to facilitate the charting process.
     */
    public Month toJFreeMonth() {
        return new Month(this.month, this.year);
    }

    /**
     * Returns the current WasteMonth
     */
    public static WasteMonth getCurrentWasteMonth() {
        return new WasteMonth(LocalDate.now());
    }

    /**
     * Given two WasteMonth objects, returns the month which is earlier.
     *
     * @return the earlier of the two waste months.
     */
    public static WasteMonth earlier(WasteMonth wm1, WasteMonth wm2) {
        return (wm1.isBefore(wm2)) ? wm1 : wm2;
    }

    /**
     * Given two WasteMonth objects, returns the month which is later.
     *
     * @return the later of the two waste months.
     */
    public static WasteMonth later(WasteMonth wm1, WasteMonth wm2) {
        return (wm1.isAfter(wm2)) ? wm1 : wm2;
    }

    @Override
    public int compareTo(WasteMonth otherMonth) {
        LocalDate otherDate = LocalDate.of(otherMonth.getYear(), otherMonth.getMonth(), 1);
        LocalDate thisDate = LocalDate.of(this.getYear(), this.getMonth(), 1);
        return thisDate.compareTo(otherDate);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WasteMonth)) {
            return false;
        }
        boolean isSameMonth = ((WasteMonth) other).getMonth() == this.getMonth();
        boolean isSameYear = ((WasteMonth) other).getYear() == this.getYear();
        return isSameMonth && isSameYear;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
        YearMonth yearMonth = YearMonth.of(this.year, this.month);
        return formatter.format(yearMonth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }
}
