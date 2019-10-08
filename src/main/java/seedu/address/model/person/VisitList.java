package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class VisitList {
    //public final String value = "";
    public static final String MESSAGE_CONSTRAINTS = "Visit date should follow dd/mm/yyyy format";
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";

    private ArrayList<VisitReport> records = new ArrayList<>();

    public VisitList(ArrayList<VisitReport> lst) {
        //requireNonNull(lst);
        records = lst;
    }

    public VisitList addRecord(VisitReport report) {
        records.add(report);
        return this;
    }

    public ArrayList<VisitReport> getRecords() {
        return this.records;
    }

    public ObservableList<VisitReport> getObservableRecords() {
        return FXCollections.observableArrayList(this.records);
    }

    @Override
    public String toString() {
        return records.toString();
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidVisitDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public VisitList deleteRecord(int id) throws IndexOutOfBoundsException {
        this.records.remove(id - 1);
        return this;
    }
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitList // instanceof handles nulls
                && value.equals(((VisitList) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    */


}
