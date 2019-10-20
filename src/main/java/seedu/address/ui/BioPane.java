package seedu.address.ui;

import java.io.File;
import java.util.List;

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
    private static final String DEFAULT_DP_PATH = "/images/user.png";

    private BioTable bioTable;
    private Profile profile;
    private boolean dpExists;
    private Image img;

    private String name;
    private String nric;
    private String gender;
    private String dob;
    private String hp;
    private String emergencyHp;
    private String medicalCondition;
    private String address;
    private String dpPath;
    private String bgColour;
    private String fontColour;
    private String myGoals;
    private String otherBioInfo;

    @FXML
    private HBox profilePlaceholder;

    @FXML
    private VBox bioTablePlaceholder;

    public BioPane(ObservableList<User> filteredUserList) {
        super(FXML);

        if (!filteredUserList.isEmpty()) {

            User user = filteredUserList.get(0);

            name = user.getName().toString();
            nric = user.getNric().toString();
            gender = user.getGender().toString();
            dob = user.getDateOfBirth().toString();
            hp = listToString(user.getContactNumbers());;
            emergencyHp = listToString(user.getEmergencyContacts());;
            medicalCondition = listToString(user.getMedicalConditions());
            address = user.getAddress().toString();
            dpPath = user.getDpPath().toString();
            bgColour = "navy-blue";
            fontColour = "yellow";
            myGoals = listToString(user.getGoals());
            otherBioInfo = user.getOtherBioInfo().toString();

            File file = new File(dpPath);

            if (!file.exists()) {
                if (img == null) {
                    img = new Image(MainApp.class.getResourceAsStream(DEFAULT_DP_PATH));
                };
            } else {
                img = new Image(file.toURI().toString());
            }

            profile = new Profile(img, user.getName().toString(), user.getProfileDesc().toString());
            profilePlaceholder.getChildren().add(profile.getRoot());
            bioTable = new BioTable(name, nric, gender, dob, hp, emergencyHp, medicalCondition, address, dpPath, bgColour,
                    fontColour, myGoals, otherBioInfo);
        } else {
            Image img = new Image(MainApp.class.getResourceAsStream(DEFAULT_DP_PATH));
            profile = new Profile(img, "No user bio added :(", null);
            profilePlaceholder.getChildren().add(profile.getRoot());
            bioTable = new BioTable();
        }
        bioTablePlaceholder.getChildren().add(bioTable.getRoot());
    }

    /**
     * Returns the String representation of a list that is to be displayed in this BioPane.
     * @param list A list for which its String representation is to be representation.
     * @return String representation of a list that is to be displayed in this BioPane.
     */
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
