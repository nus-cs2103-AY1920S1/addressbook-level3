package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Panel containing the introduction screen.
 */
public class TitleScreenPanel extends UiPart<Region> {
    private static final String FXML = "TitleScreenPanel.fxml";
    private static final String MESSAGE_INTRODUCTION = "Welcome to Dukemon!";
    private static final String MESSAGE_INFORMATION = "Welcome to the world of Dukemon, user!\n\n"
            + "Type \"load [WORDBANK NUMBER]\" to load a word bank of your choice.\n"
            + "Type \"start\" to immediately start the default word bank immediately.";

    @FXML
    private Label introduction;

    @FXML
    private Label body;

    public TitleScreenPanel() {
        super(FXML);
        introduction.setText(MESSAGE_INTRODUCTION);
        body.setText(MESSAGE_INFORMATION);
    }

}
