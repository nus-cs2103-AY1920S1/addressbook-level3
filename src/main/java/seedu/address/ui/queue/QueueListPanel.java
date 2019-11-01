package seedu.address.ui.queue;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReferenceId;
import seedu.address.model.ReferenceIdResolver;
import seedu.address.model.person.Person;
import seedu.address.model.queue.Room;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class QueueListPanel extends UiPart<Region> {
    private static final String FXML = "queue/QueueListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QueueListPanel.class);
    private int queueCount;
    private int serveCount;
    private ObservableList<ReferenceId> queueList;
    private ObservableList<Room> consultationRoomList;

    @FXML
    private Label nowServing;
    @FXML
    private Label inQueue;
    @FXML
    private ListView<Room> roomListView;
    @FXML
    private ListView<ReferenceId> queueListView;

    public QueueListPanel(ObservableList<Room> consultationRoomList, ObservableList<ReferenceId> queueList,
                          ReferenceIdResolver resolver) {
        super(FXML);
        this.queueList = queueList;
        this.consultationRoomList = consultationRoomList;
        roomListView.setItems(consultationRoomList);
        roomListView.setCellFactory(listView -> new RoomListViewCell(resolver));
        queueListView.setItems(queueList);
        queueListView.setCellFactory(listView -> new QueueListViewCell(resolver));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class QueueListViewCell extends ListCell<ReferenceId> {
        private final ReferenceIdResolver resolver;

        public QueueListViewCell(ReferenceIdResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        protected void updateItem(ReferenceId id, boolean empty) {
            super.updateItem(id, empty);
            queueCount = queueList.size();
            inQueue.setText(queueCount + " In Queue");
            if (empty || id == null) {
                setGraphic(null);
            } else {
                Person person = resolver.resolvePatient(id);
                setGraphic(new QueueCard(person, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Room} using a {@code RoomCard}.
     */
    class RoomListViewCell extends ListCell<Room> {
        private final ReferenceIdResolver resolver;

        public RoomListViewCell(ReferenceIdResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        protected void updateItem(Room room, boolean empty) {
            super.updateItem(room, empty);
            serveCount = getServeCount();
            nowServing.setText("Now Serving (" + serveCount + ")");
            //serveCount = consultationRoomList.
            if (empty || room == null) {
                setGraphic(null);
                setText(null);
            } else {
                Person doctor = resolver.resolveStaff(room.getDoctor());
                Optional<Person> patient = room.getCurrentPatient().map(id -> resolver.resolvePatient(id));
                setGraphic(new RoomCard(doctor, patient, getIndex() + 1, room.isReadyToServe()).getRoot());
            }
        }

        private int getServeCount() {
            int count = 0;
            for (Room room : consultationRoomList) {
                if (!room.getCurrentPatient().equals(Optional.empty())) {
                    count++;
                }
            }
            return count;
        }
    }

}
