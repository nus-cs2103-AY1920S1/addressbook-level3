package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.CommandRecord;

/**
 * Represent
 */
public class CommandRecordCard extends UiPart<Region> {
    private static final String FXML = "CommandRecordCard.fxml";

    public final CommandRecord commandRecord;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private HBox cardPane;
    @FXML
    private VBox labels;

    @FXML
    private Label commandString; //can be team name, mentor name or participant name
    @FXML
    private Label index;

    /**
     * Constructs a new instance of Command Record Card.
     *
     * @param commandRecord CommandRecord to make the Card out of
     */
    public CommandRecordCard(CommandRecord commandRecord) {
        super(FXML);

        this.commandRecord = commandRecord;
        ;
        commandString.setText(commandRecord.getCommandString());
        CommandRecord.CommandType commandType = commandRecord.getCommandType();

        if (!((commandType.equals(CommandRecord.CommandType.END))
                || (commandType.equals(CommandRecord.CommandType.CURR)))) {
            index.setText(commandRecord.getIndex().toString());
        }

        switch (commandType) {
        case UNDO:
            cardPane.setStyle("-fx-background-color: #17202a");
            break;
        case CURR:
            labels.getChildren().add(new javafx.scene.control.Label("You are here"));
            cardPane.setStyle("-fx-background-color: #17202a");
            break;
        case REDO:
            cardPane.setStyle("-fx-background-color: #17202b");
            break;
        case END:
            cardPane.setStyle("-fx-background-color: #17202c");
            break;

        default:
            assert false;
            logger.severe("CommandRecord has incorrect CommandType");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntityCard)) {
            return false;
        }

        // state check(if two EntityCard are equal)
        CommandRecordCard card = (CommandRecordCard) other;
        return index.getText().equals(card.index.getText())
                && commandRecord.equals(card.commandRecord)
                && commandString.getText().equals(card.commandString.getText());
    }
}
