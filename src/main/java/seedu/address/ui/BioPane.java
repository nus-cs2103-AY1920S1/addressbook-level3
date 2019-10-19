package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.MainApp;
import seedu.address.model.bio.User;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class BioPane extends UiPart<Region> {

    private static final String FXML = "BioPane.fxml";
    private static String displayImage = "/images/user.png";

    private BioTable bioTable = new BioTable();
    private Profile profile;

    @FXML
    private HBox profilePlaceholder;

    @FXML
    private VBox bioTablePlaceholder;

    public BioPane(ObservableList<User> filteredUserList) {
        super(FXML);
        Image img = new Image(MainApp.class.getResourceAsStream(displayImage));

        if (!filteredUserList.isEmpty()) {
            User user = filteredUserList.get(0);

            profile = new Profile(img, user.getName().toString(), user.getProfileDesc().toString());
            profilePlaceholder.getChildren().add(profile.getRoot());

            BioTableFieldDataPair name = new BioTableFieldDataPair("Name:", user.getName().toString());
            BioTableFieldDataPair nric = new BioTableFieldDataPair("NRIC:", user.getNric().toString());
            BioTableFieldDataPair gender = new BioTableFieldDataPair("Gender:", user.getGender().toString());
            BioTableFieldDataPair dob = new BioTableFieldDataPair("DOB:", user.getDateOfBirth().toString());
            BioTableFieldDataPair hp = new BioTableFieldDataPair("HP:",
                    listToString(user.getContactNumbers()));;
            BioTableFieldDataPair emergencyHp = new BioTableFieldDataPair("Emergency HP:",
                    listToString(user.getEmergencyContacts()));;
            BioTableFieldDataPair medicalCondition = new BioTableFieldDataPair("Medical Condition:",
                    listToString(user.getMedicalConditions()));
            BioTableFieldDataPair address = new BioTableFieldDataPair("Address:", user.getAddress().toString());
            BioTableFieldDataPair dpPath = new BioTableFieldDataPair("DP Path:",
                    "/Users/Amy/dp.png");
            BioTableFieldDataPair bgColour = new BioTableFieldDataPair("Background Colour:", "navy-blue");
            BioTableFieldDataPair fontColour = new BioTableFieldDataPair("Font Colour:", "yellow");
            BioTableFieldDataPair myGoals = new BioTableFieldDataPair("My Goals:",
                    listToString(user.getGoals()));;
            BioTableFieldDataPair otherBioInfo = new BioTableFieldDataPair("Other Bio Info:",
                    user.getOtherBioInfo().toString());
            ObservableList<BioTableFieldDataPair> list = FXCollections.observableArrayList();
            list.addAll(name, nric, gender, dob, hp, emergencyHp, medicalCondition, address, dpPath, bgColour,
                    fontColour, myGoals, otherBioInfo);

            bioTable.getTableView().setItems(list);
        } else {
            profile = new Profile(img, "No user bio added :(", null);
            profilePlaceholder.getChildren().add(profile.getRoot());
        }
        bioTablePlaceholder.getChildren().add(bioTable.getRoot());
    }

    private static String listToString(List<? extends Object> list) {
        StringBuilder stringBuilder = new StringBuilder();

        if (list.size() == 1) {
            return list.get(0).toString();
        }

        for (int i = 1; i <= list.size(); i++) {
            if (i > 1) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(i).append(". ").append(list.get(i - 1));

        }
        return stringBuilder.toString();
    }

}
