package seedu.address.ui;

import java.util.Comparator;

import com.sun.javafx.scene.control.skin.Utils;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import seedu.address.commons.Keywords;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

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
