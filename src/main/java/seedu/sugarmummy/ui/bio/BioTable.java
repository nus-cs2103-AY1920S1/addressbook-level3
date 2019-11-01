package seedu.sugarmummy.ui.bio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;
import seedu.sugarmummy.ui.UiPart;


/**
 * A ui for the table that is used to display the user's biography.
 */
public class BioTable extends UiPart<Region> {

    private static final String FXML = "BioTable.fxml";
    private static final List<String> FIELD_LABELS = new ArrayList<>(List.of("Name:", "NRIC:", "Gender:", "DOB:",
            "HP:", "Emergency HP:", "Medical Condition:", "Address:", "DP Path:", "Font Colour:", "Background:",
            "Background Size:", "Background Repeat:", "My Goals:", "Other Bio Info"));
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
        //        setFontColourToColumn(field, "yellow");
        data.setCellValueFactory(new PropertyValueFactory<String, String>("data"));
        //        setFontColourToColumn(data, "red");
        setTextWrapping(field);
        setTextWrapping(data);
        tableView.setSelectionModel(null);
        list = FXCollections.observableArrayList();
        FIELD_LABELS.forEach(fieldLabel -> list.add(new BioTableFieldDataPair(fieldLabel, "")));
        tableView.setItems(list);
    }

    /**
     * Creates a biotable with the user's specified fields.
     */
    public BioTable(String name, String nric, String gender, String dob, String hp, String emergencyHp,
                    String medicalCondition, String address, String dpPath, String fontColour, String bg,
                    String bgSize, String bgRepeat, String myGoals, String otherBioInfo) {
        this();

        List<String> data = new ArrayList<>(List.of(name, nric, gender, dob, hp, emergencyHp, medicalCondition, address,
                dpPath, fontColour, bg, bgSize, bgRepeat, myGoals, otherBioInfo));
        Iterator<String> dataIterator = data.iterator();
        list = FXCollections.observableArrayList();
        FIELD_LABELS.forEach(fieldLabel -> list.add(new BioTableFieldDataPair(fieldLabel, dataIterator.next())));
        tableView.setItems(list);

    }

    /**
     * Sets individual font colours to columns other than those in the header column
     *
     * @param column Column for which colour is to be set.
     * @param colour Colour to be set to the selected column.
     */
    public void setFontColourToColumn(TableColumn<String, String> column, String colour) {
        column.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
            @Override
            public TableCell<String, String> call(TableColumn<String, String> param) {
                return new TableCell<String, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            setStyle("-fx-text-fill: " + colour + " !important;");
                            setText(item);
                        }
                    }
                };
            }
        });
    }

    /**
     * Sets the given table column to wrap text.
     *
     * @param column Column for which text is to be wrapped.
     */
    public void setTextWrapping(TableColumn<String, String> column) {
        column.setCellFactory(tc -> {
            TableCell<String, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setText(text.toString());
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(column.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            text.getStyleClass().add("table-cell-text");
            return cell;
        });
    }

}
