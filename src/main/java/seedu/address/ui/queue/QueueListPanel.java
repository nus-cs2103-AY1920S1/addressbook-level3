package seedu.address.ui.queue;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.queue.Room;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class QueueListPanel extends UiPart<Region> {
    private static final String FXML = "queue/QueueListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QueueListPanel.class);

    @FXML
    private Label nowServing;
    @FXML
    private Label inQueue;
    @FXML
    private ListView<Room> roomListView;
    @FXML
    private ListView<Person> queueListView;

    public QueueListPanel(ObservableList<Room> consultationRoomList, ObservableList<Person> queueList) {
        super(FXML);
        roomListView.setItems(consultationRoomList);
        roomListView.setCellFactory(listView -> new RoomListViewCell());
        queueListView.setItems(queueList);
        queueListView.setCellFactory(listView -> new QueueListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class QueueListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QueueCard(person, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Room} using a {@code RoomCard}.
     */
    class RoomListViewCell extends ListCell<Room> {
        @Override
        protected void updateItem(Room room, boolean empty) {
            super.updateItem(room, empty);

            if (empty || room == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RoomCard(room, getIndex() + 1).getRoot());
            }
        }
    }

}
