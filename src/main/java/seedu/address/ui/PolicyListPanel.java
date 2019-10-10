package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.policy.Policy;

/**
 * Panel containing the list of policies.
 */
public class PolicyListPanel extends UiPart<Region> {
    private static final String FXML = "PolicyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Policy> policyListView;

    public PolicyListPanel(ObservableList<Policy> policyList) {
        super(FXML);
        policyListView.setItems(policyList);
        policyListView.setCellFactory(listView -> new policyListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Policy} using a {@code PolicyCard}.
     */
    class policyListViewCell extends ListCell<Policy> {
        @Override
        protected void updateItem(Policy policy, boolean empty) {
            super.updateItem(policy, empty);

            if (empty || policy == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PolicyCard(policy, getIndex() + 1).getRoot());
            }
        }
    }

}
