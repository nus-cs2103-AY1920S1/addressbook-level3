package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Person's List of records in the address book.
 *
 */
public class VisitList {

    public static final String MESSAGE_CONSTRAINTS = "Visit date should follow dd/mm/yyyy format";
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";

    private ArrayList<VisitReport> records = new ArrayList<>();

    public VisitList(ArrayList<VisitReport> lst) {
        //requireNonNull(lst);
        records = lst;
    }

    /**
     * Adds a new record to the visit list.
     *
     */
    public VisitList addRecord(VisitReport report) {
        this.records.add(report);
        Collections.sort(this.records);
        return this;
    }

    public VisitReport getRecordByIndex(int id) throws IndexOutOfBoundsException {
        return records.get(id - 1);
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

    public VisitList editRecord(int reportIdx, VisitReport visitReport) {
        this.records.set(reportIdx - 1, visitReport);
        return this;
    }

    /**
     * Deletes record from the list by index.
     */
    public VisitList deleteRecord(int id) throws IndexOutOfBoundsException {
        this.records.remove(id - 1);
        return this;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitList // instanceof handles nulls
                && records.equals(((VisitList) other).records)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(records);
    }



}
