package seedu.address.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;


/**
 * A ui for the table that is used to display the user's biography.
 */
public class BioTable extends UiPart<Region> {

    private static final String FXML = "BioTable.fxml";
    private static final List<String> FIELD_LABELS = new ArrayList<>(List.of("Name:", "NRIC:", "Gender:", "DOB:",
            "HP:", "Emergency HP:", "Medical Condition:", "Address:", "DP Path:", "Background Colour:", "Font Colour:",
            "My Goals:", "Other Bio Info"));
    private ObservableList<BioTableFieldDataPair> list;

    @FXML
    private TableView<BioTableFieldDataPair> tableView;

    @FXML
    private TableColumn<String, String> field;

    @FXML
    private TableColumn<String, String> data;

    public BioTable() {
        super(FXML);
        field.setCellValueFactory(new PropertyValueFactory<String, String>("field"));
        data.setCellValueFactory(new PropertyValueFactory<String, String>("data"));
        tableView.setSelectionModel(null);
        list = FXCollections.observableArrayList();
        FIELD_LABELS.forEach(fieldLabel -> list.add(new BioTableFieldDataPair(fieldLabel, "")));
        tableView.setItems(list);
    }

    /**
     * Creates a biotable with the user's specified fields.
     */
    public BioTable(String name, String nric, String gender, String dob, String hp, String emergencyHp,
                    String medicalCondition, String address, String dpPath, String bgColour, String fontColour,
                    String myGoals, String otherBioInfo) {
        this();

        List<String> data = new ArrayList<>(List.of(name, nric, gender, dob, hp, emergencyHp, medicalCondition, address,
                dpPath, bgColour, fontColour, myGoals, otherBioInfo));
        Iterator<String> dataIterator = data.iterator();
        list = FXCollections.observableArrayList();
        FIELD_LABELS.forEach(fieldLabel -> list.add(new BioTableFieldDataPair(fieldLabel, dataIterator.next())));
        tableView.setItems(list);
    }

}
