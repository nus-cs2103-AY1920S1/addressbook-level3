package seedu.revision.ui.statistics;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.quiz.Statistics;
import seedu.revision.ui.UiPart;

/**
 * Panel containing the list of statistics.
 */
public class StatisticsListPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsListPanel.class);

    @FXML
    private ListView<Statistics> statisticsListView;

    public StatisticsListPanel(ObservableList<Statistics> statisticsList) {
        super(FXML);
        statisticsListView.setItems(statisticsList);
        statisticsListView.setCellFactory(listView -> new StatisticsListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Statistics} using a {@code StatisticsCard}.
     */
    class StatisticsListViewCell extends ListCell<Statistics> {
        @Override
        protected void updateItem(Statistics statistics, boolean empty) {
            super.updateItem(statistics, empty);

            if (empty || statistics == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StatisticsCard(statistics, getIndex() + 1).getRoot());
            }
        }
    }

}

