package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * A class that fields to display in the bioTable displayed to the user.
 * Reference to example from: https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
 */
public class BioTableFieldDataPair {

    private final SimpleStringProperty field;
    private final SimpleStringProperty data;

    BioTableFieldDataPair(String field, String data) {
        this.field = new SimpleStringProperty(field);
        this.data = new SimpleStringProperty(data);
    }

    public String getField() {
        return field.get();
    }

    public void setField(String field) {
        this.field.set(field);
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.set(data);
    }
}
