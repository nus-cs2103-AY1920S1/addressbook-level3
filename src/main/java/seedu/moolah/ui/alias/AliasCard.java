package seedu.moolah.ui.alias;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.ui.UiPart;

/**
 * An UI component that displays information of a {@code Alias}.
 */
public class AliasCard extends UiPart<Region> {

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MooLah level 4</a>
     */

    private static final String FXML = "AliasCard.fxml";
    @FXML
    private AnchorPane aliasCardPane;
    @FXML
    private Label aliasName;
    @FXML
    private Label aliasInput;
    private Alias alias;

    public AliasCard(Alias alias, int maxLength) {
        super(FXML);
        this.alias = alias;
        int whiteSpaceCount = maxLength - alias.getAliasName().length();
        aliasName.setText(" ".repeat(whiteSpaceCount) + alias.getAliasName());
        aliasInput.setText("  " + alias.getInput());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AliasCard)) {
            return false;
        }

        // state check
        AliasCard card = (AliasCard) other;
        return aliasInput.getText().equals(card.aliasInput.getText())
                && aliasName.getText().equals(card.aliasName.getText())
                && alias.equals(card.alias);
    }
}
