package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.claim.Claim;

//@@author{weigenie}
/**
 * Panel containing the list of persons.
 */
public class ClaimListPanel extends UiPart<Region> {
    private static final String FXML = "ClaimListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ClaimListPanel.class);

    @FXML
    private ListView<Claim> claimListView;

    public ClaimListPanel(ObservableList<Claim> claimList) {
        super(FXML);
        claimListView.setItems(claimList);
        claimListView.setCellFactory(listView -> new ClaimListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Claim} using a {@code ClaimCard}.
     */
    class ClaimListViewCell extends ListCell<Claim> {
        @Override
        protected void updateItem(Claim claim, boolean empty) {
            super.updateItem(claim, empty);

            if (empty || claim == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClaimCard(claim, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Clears selection of the view.
     */
    public void clearSelection() {
        Platform.runLater(() -> {
            claimListView.getSelectionModel().clearSelection();
        });
    }

    /**
     * Change list of view.
     * @param claimList updated list.
     */
    public void updatePanel(ObservableList<Claim> claimList) {
        claimListView.setItems(claimList);
        claimListView.setCellFactory(listView -> new ClaimListViewCell());
    }

}
