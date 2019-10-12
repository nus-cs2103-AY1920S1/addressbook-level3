package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.person.Person;

import java.util.Comparator;

public class CheatSheetCard extends UiPart<Region> {
    private static final String FXML = "CheatSheetListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final CheatSheet cheatSheet;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public CheatSheetCard(CheatSheet cheatSheet, int displayedIndex) {
        super(FXML);
        this.cheatSheet = cheatSheet;
        id.setText(displayedIndex + ". ");
        name.setText(cheatSheet.getTitle().value);
        cheatSheet.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheatSheetCard)) {
            return false;
        }

        // state check TODO
        CheatSheetCard card = (CheatSheetCard) other;
        return id.getText().equals(card.id.getText());
    }
}
