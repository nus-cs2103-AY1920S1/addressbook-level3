package seedu.jarvis.ui.history;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.ui.UiPart;

/**
 * An UI component that displays information of a {@code Command} that can be undone or redone.
 */
public class ActionCard extends UiPart<Region> {
    private static final String FXML = "ActionListCard.fxml";

    public final Command command;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;

    public ActionCard(Command command, int displayedIndex) {
        super(FXML);
        this.command = command;
        id.setText(displayedIndex + ". ");
        description.setText(command.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ActionCard)) {
            return false;
        }

        ActionCard card = (ActionCard) obj;
        return id.getText().equals(card.id.getText())
                && command.equals(card.command);
    }
}
