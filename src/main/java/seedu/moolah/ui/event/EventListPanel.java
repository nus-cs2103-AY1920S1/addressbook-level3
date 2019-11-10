package seedu.moolah.ui.event;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import seedu.moolah.commons.core.LogsCenter;
import seedu.moolah.model.expense.Event;
import seedu.moolah.ui.panel.Panel;
import seedu.moolah.ui.panel.PanelName;

/**
 * Panel containing the list of expenses.
 */
public class EventListPanel extends Panel {
    public static final PanelName PANEL_NAME = new PanelName("Event List");
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(seedu.moolah.ui.event.EventListPanel.class);

    @FXML
    private StackPane titlePlaceHolder;
    @FXML
    private ListView<Event> listView;

    public EventListPanel(ObservableList<Event> eventList, boolean withTitle) {
        super(FXML);

        titlePlaceHolder.setMinHeight(0);
        if (withTitle) {
            titlePlaceHolder.getChildren().add(new Label("Event List"));
        }
        listView.setItems(eventList);
        listView.setCellFactory(listView -> new EventListViewCell());

    }

    @Override
    public void view() {
        getRoot().setVisible(true);
        getRoot().setDisable(false);
    }

    @Override
    public void hide() {
        getRoot().setVisible(false);
        getRoot().setDisable(true);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }

}
