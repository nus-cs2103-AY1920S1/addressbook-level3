package cs.f10.t1.nursetraverse.ui;

import java.util.Optional;

import cs.f10.t1.nursetraverse.model.HistoryRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code HistoryRecord}.
 */
public class HistoryRecordCard extends UiPart<Region> {

    private static final String FXML = "HistoryRecordCard.fxml";

    public final HistoryRecord historyRecord;

    @FXML
    private Label commandText;

    public HistoryRecordCard(HistoryRecord record) {
        super(FXML);
        this.historyRecord = record;

        Optional<String> commandTextOptional = record.getCommand().getCommandText();
        if (commandTextOptional.isPresent()) {
            commandText.setText(commandTextOptional.get());
        } else {
            // This should not happen, but defensively display the debug string
            commandText.setText(record.toString());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryRecordCard)) {
            return false;
        }

        // state check
        HistoryRecordCard card = (HistoryRecordCard) other;
        return historyRecord.equals(card.historyRecord);
    }
}
