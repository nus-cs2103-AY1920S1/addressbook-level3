package seedu.address.achievements.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AchievementsProgressBar extends UiPart<Region> {

    private static final String FXML = "AchievementsProgressBar.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox progressBarPlaceholder;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progress;

    public AchievementsProgressBar(double progress) {
        super(FXML);
        progressBar.setProgress(progress);
        this.progress.setText(String.format("%.1f%%", progress * 100));
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
