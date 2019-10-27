package seedu.address.achievements.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AchievementsCard extends UiPart<Region> {

    private static final String FXML = "AchievementsCard.fxml";

    @FXML
    private HBox cardPane;

    @FXML
    private VBox cardPlaceholder;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


    public AchievementsCard() {
        super(FXML);
        cardPlaceholder.getChildren().addAll(new AchievementsTitleLabel("Total Contacts: ", "10 / 200").getRoot(), new AchievementsProgressBar(0.5).getRoot());
    }

//    @Override
//    public boolean equals(Object other) {
//        // short circuit if same object
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof AchievementsCard)) {
//            return false;
//        }
//
//        // state check
//        AchievementsCard card = (AchievementsCard) other;
//        return id.getText().equals(card.id.getText())
//                && person.equals(card.person);
//    }
}
