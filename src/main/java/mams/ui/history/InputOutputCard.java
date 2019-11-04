package mams.ui.history;

import static java.util.Objects.requireNonNull;

import static mams.commons.util.CollectionUtil.requireAllNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.commons.core.index.Index;
import mams.logic.history.InputOutput;
import mams.ui.UiPart;

/**
 * An UI component that displays information of an {@code InputOutput} for display in HistoryWindow.
 */
public class InputOutputCard extends UiPart<Region> {

    public static final String STATUS_SUCCESSFUL_COMMAND = "\u2713" + " Successful Execution";
    public static final String STATUS_UNSUCCESSFUL_COMMAND = "\u2718" + " Failed Execution";

    public static final String UNSUCCESSFUL_STYLE_CLASS = "red-text-label";
    public static final String SUCCESSFUL_STYLE_CLASS = "green-text-label";

    private static final String FXML = "InputOutputListCard.fxml";

    private final InputOutput inputOutput;
    private final Index positionOnDisplay;

    @FXML
    private HBox cardPane;
    @FXML
    private Label input;
    @FXML
    private Label output;
    @FXML
    private Label executionStatus;
    @FXML
    private Label timeStamp;

    public InputOutputCard(InputOutput inputOutput, int displayedListIndex, boolean isHideOutput) {
        super(FXML);
        requireNonNull(inputOutput);
        this.inputOutput = inputOutput;
        positionOnDisplay = Index.fromOneBased(displayedListIndex);
        input.setText(inputOutput.getInput());
        output.setText((isHideOutput) ? "" : inputOutput.getOutput());
        timeStamp.setText(inputOutput.getTimeStampAsString());
        setExecutionStatusDisplay(executionStatus, output, inputOutput.checkSuccessful());
    }

    private static void setExecutionStatusDisplay(Label executionStatus, Label output, boolean isSuccessfulCommand) {
        requireAllNonNull(executionStatus, output);
        if (isSuccessfulCommand) {
            executionStatus.setText(STATUS_SUCCESSFUL_COMMAND);
            executionStatus.getStyleClass().add(SUCCESSFUL_STYLE_CLASS);
        } else {
            executionStatus.setText(STATUS_UNSUCCESSFUL_COMMAND);
            executionStatus.getStyleClass().add(UNSUCCESSFUL_STYLE_CLASS);
            output.getStyleClass().add(UNSUCCESSFUL_STYLE_CLASS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InputOutputCard)) {
            return false;
        }

        // state check
        InputOutputCard card = (InputOutputCard) other;
        return positionOnDisplay == card.positionOnDisplay
                && inputOutput.equals(card.inputOutput);
    }
}
