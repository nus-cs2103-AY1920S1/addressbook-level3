package seedu.address.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

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