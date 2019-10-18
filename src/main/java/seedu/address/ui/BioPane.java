package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.MainApp;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class BioPane extends UiPart<Region> {

    private static final String FXML = "BioPane.fxml";
    private static String displayImage = "/images/user.png";

    private BioTable bioTable;
    private Profile profile;

    @FXML
    private HBox profilePlaceholder;

    @FXML
    private VBox bioTablePlaceholder;

    public BioPane() {
        super(FXML);
        Image img = new Image(MainApp.class.getResourceAsStream(displayImage));

        // SAMPLE DATA
        BioTableFieldDataPair name = new BioTableFieldDataPair("Name:", "Amy");
        BioTableFieldDataPair nric = new BioTableFieldDataPair("NRIC:", "S1234567Z");
        BioTableFieldDataPair gender = new BioTableFieldDataPair("Gender:", "Female");
        BioTableFieldDataPair dob = new BioTableFieldDataPair("DOB:", "21/03/1940");
        BioTableFieldDataPair hp = new BioTableFieldDataPair("HP:", "98765432");
        BioTableFieldDataPair emergencyHp = new BioTableFieldDataPair("Emergency HP:", "91234567");
        BioTableFieldDataPair medicalCondition = new BioTableFieldDataPair("Medical Condition:",
                "Type II Diabetes, High Blood Pressure");
        BioTableFieldDataPair address = new BioTableFieldDataPair("Address:",
                "Blk 123 Example Road\n#12-34\nS(612345)");
        BioTableFieldDataPair dpPath = new BioTableFieldDataPair("DP Path:",
                "/Users/Amy/dp.png");
        BioTableFieldDataPair bgColour = new BioTableFieldDataPair("Background Colour:", "navy-blue");
        BioTableFieldDataPair fontColour = new BioTableFieldDataPair("Font Colour:", "yellow");
        BioTableFieldDataPair myGoals = new BioTableFieldDataPair("My Goals:",
                "lose 4kg from 29/09/2019 to 30/09/2019");

        ObservableList<BioTableFieldDataPair> list = FXCollections.observableArrayList();
        list.addAll(name, nric, gender, dob, hp, emergencyHp, medicalCondition, address, dpPath, bgColour,
                fontColour, myGoals);

        profile = new Profile(img, "Amy", "\"If at first you don't succeed, call it version 1.0."
                + "\"\n-Anonymous");
        profilePlaceholder.getChildren().add(profile.getRoot());

        bioTable = new BioTable();
        bioTable.getTableView().setItems(list);
        bioTablePlaceholder.getChildren().add(bioTable.getRoot());
    }
}
