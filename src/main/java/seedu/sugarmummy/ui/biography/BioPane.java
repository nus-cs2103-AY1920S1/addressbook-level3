package seedu.sugarmummy.ui.biography;

import java.io.File;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.MainApp;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.biography.User;
import seedu.sugarmummy.ui.UiPart;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where other JavaFX elements
 * can be placed.
 */
public class BioPane extends UiPart<Region> {

    private static final String FXML = "BioPane.fxml";
    private static final String DEFAULT_DP_PATH = "/images/user.png";

    private BioTable bioTable;
    private Profile profile;
    private Image img;
    private String dpPath;

    @FXML
    private HBox profilePlaceholder;

    @FXML
    private VBox bioTablePlaceholder;

    public BioPane(ObservableList<User> filteredUserList, Colour fontColour, Background background) {
        this(filteredUserList, null, fontColour, background);
    }

    public BioPane(ObservableList<User> filteredUserList, Image img, Colour fontColour, Background background) {
        super(FXML);

        if (!filteredUserList.isEmpty()) {

            User user = filteredUserList.get(0);

            dpPath = user.getDpPath().toString();

            String name = user.getName().toString();
            String nric = user.getNric().toString();
            String gender = user.getGender().toString();
            String dob = user.getDateOfBirth().toString();
            String hp = listToString(user.getContactNumbers());
            String emergencyHp = listToString(user.getEmergencyContacts());
            String medicalCondition = listToString(user.getMedicalConditions());
            String address = user.getAddress().toString();
            String fontColourToString = fontColour.toString();
            String bg = background.toString();
            String bgSize = background.getBgSize();
            String bgRepeat = background.getBgRepeat();
            String myGoals = listToString(user.getGoals());
            String otherBioInfo = user.getOtherBioInfo().toString();

            File file = new File(dpPath);

            if (img != null) {
                profile = new Profile(img, user.getName().toString(), user.getProfileDesc().toString());
            } else {
                if (!file.exists()) {
                    if (img == null) {
                        img = new Image(MainApp.class.getResourceAsStream(DEFAULT_DP_PATH));
                    }
                } else {
                    img = new Image(file.toURI().toString());
                }

                profile = new Profile(img, user.getName().toString(), user.getProfileDesc().toString());
                this.img = img;
            }
            profilePlaceholder.getChildren().add(profile.getRoot());
            bioTable = new BioTable(name, nric, gender, dob, hp, emergencyHp, medicalCondition, address, dpPath,
                    fontColourToString, bg, bgSize, bgRepeat, myGoals, otherBioInfo);
        } else {
            img = new Image(MainApp.class.getResourceAsStream(DEFAULT_DP_PATH));
            this.img = img;
            profile = new Profile(img, "No user bio added :(", null);
            profilePlaceholder.getChildren().add(profile.getRoot());
            bioTable = new BioTable(fontColour.toString(), background.toString(), background.getBgSize(),
                    background.getBgRepeat());
        }
        bioTablePlaceholder.getChildren().add(bioTable.getRoot());
    }

    /**
     * Returns the String representation of a list that is to be displayed in this BioPane.
     *
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

    public String getDpPath() {
        return dpPath;
    }

    public Image getImg() {
        return img;
    }

}
