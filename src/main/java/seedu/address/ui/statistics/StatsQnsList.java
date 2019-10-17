package seedu.address.ui.statistics;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.statistics.TempStatsQnsModel;
import seedu.address.ui.UiPart;

/**
 * Temporary panel for returning correct/incorrect questions.
 */
public class StatsQnsList extends UiPart<Region> {
    private static final String FXML = "StatsQnsList.fxml";

    @FXML
    private Label qnsListLabel;

    public StatsQnsList(ObservableList<TempStatsQnsModel> qnsList) {
        super(FXML);
        qnsListLabel.setText(createLabel(qnsList));
    }

    public Label getLabel() {
        return qnsListLabel;
    }

    /**
     * Returns a string object from a question list.
     * @param qnsList The question list.
     * @return string A string object for the label.
     */
    private String createLabel(List<TempStatsQnsModel> qnsList) {
        String qnsListInString = "";
        int i = 1;
        for (TempStatsQnsModel qnsModel : qnsList) {
            qnsListInString += i + ". " + qnsModel.toString() + "\n";
            i++;
        }
        return qnsListInString;
    }
}
