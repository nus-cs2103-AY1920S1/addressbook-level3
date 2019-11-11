package mams.ui.appeal;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import mams.commons.core.LogsCenter;
import mams.model.appeal.Appeal;
import mams.ui.UiPart;

/**
 * Panel containing the list of appeals.
 */
public class AppealListPanel extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppealListPanel.class);

    private final ObservableList<Appeal> appealList;

    @FXML
    private ListView<Appeal> itemListView;

    public AppealListPanel(ObservableList<Appeal> appealList) {
        super(FXML);
        requireNonNull(appealList);
        this.appealList = appealList;
        itemListView.setItems(appealList);
        itemListView.setCellFactory(listView -> new AppealListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Appeal} using an {@code AppealCard}.
     */
    class AppealListViewCell extends ListCell<Appeal> {
        @Override
        protected void updateItem(Appeal appeal, boolean empty) {
            super.updateItem(appeal, empty);

            if (empty || appeal == null) {
                setGraphic(null);
                setText(null);
            } else if (appealList.size() == 1) {
                logger.fine("Displaying expanded appeal card.");
                setGraphic(new ExpandedAppealCard(appeal).getRoot());
            } else {
                setGraphic(new AppealCard(appeal, getIndex() + 1).getRoot());
            }
        }
    }
}
