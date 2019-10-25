package seedu.ifridge.model.waste;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.jfree.data.time.Month;

import seedu.ifridge.model.waste.exceptions.WasteMonthException;

/**
 * The Waste Month to index the list of waste lists.
 */
public class WasteMonth implements Comparable<WasteMonth> {

    public static final String MESSAGE_CONSTRAINTS =
            "Month of year can be in any format permissible by the Natty library.";
    public static final Pattern VALIDATION_REGEX = Pattern.compile("^(0[1-9]|1[012])-((19|2[0-9])[0-9]{2})$");

    private int month;
    private int year;

    public WasteMonth(int month, int year) {
        boolean isValidMonth = month > 0 && month < 13;
        boolean isValidYear = year > 1900 && year < 3000;
        if (!isValidMonth || !isValidYear) {
            String errorMessage = isValidMonth ? "Month is valid, " : "Month is not valid, ";
            errorMessage += isValidYear ? "Year is valid." : "Year is not valid.";
            throw new WasteMonthException(errorMessage);
        }
        this.month = month;
        this.year = year;
    }

    public WasteMonth(LocalDate date) {
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public WasteMonth(String wasteMonthString) {
        if (!VALIDATION_REGEX.matcher(wasteMonthString).matches()) {
            throw new WasteMonthException("Invalid Format.");
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

    public WasteMonth nextWasteMonth() {
        return addWasteMonth(1);
    }

    /**
     * Adds the specified number of months from the current waste month and returns a new waste month object
     */
    public WasteMonth addWasteMonth(int numberOfMonths) {
        LocalDate currentWasteMonthDate = LocalDate.of(this.year, this.month, 1);
        return new WasteMonth(currentWasteMonthDate.plusMonths(numberOfMonths));
    }

    public WasteMonth previousWasteMonth() {
        return minusWasteMonth(1);
    }

    /**
     * Subtracts the specified number of months from the current waste month and returns a new waste month object
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

    public Month toJFreeMonth() {
        return new Month(this.month, this.year);
    }

    public static WasteMonth getCurrentWasteMonth() {
        return new WasteMonth(LocalDate.now());
    }

    public static WasteMonth earlier(WasteMonth wm1, WasteMonth wm2) {
        return (wm1.isBefore(wm2)) ? wm1 : wm2;
    }

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
}
