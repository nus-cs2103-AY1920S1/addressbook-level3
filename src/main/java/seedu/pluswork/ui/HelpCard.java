package seedu.pluswork.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.Keywords;

/**
 * An UI component that displays information of a {@code Command}.
 */
public class HelpCard extends UiPart<Region> {

    private static final String FXML = "HelpCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ProjectDashboard level 4</a>
     */

    public final String command;

    @FXML
    private HBox cardPane;
    @FXML
    private Label commandTitle;
    @FXML
    private Label id;
    @FXML
    private Label syntax;

    public HelpCard(String command, int displayedIndex) {
        super(FXML);
        this.command = command;
        id.setText(displayedIndex + ". ");
        commandTitle.setText(command);
        syntax.setText(Keywords.getParameters(command));
    }
}
