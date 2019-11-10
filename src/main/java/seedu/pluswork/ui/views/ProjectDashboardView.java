package seedu.pluswork.ui.views;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.ui.UiPart;

/**
 * Panel containing the dashboard, with tasks separated by status.
 * Is the default view called by {@code UserViewUpdate}.
 */
public class ProjectDashboardView extends UiPart<Region> {
    private static final String FXML = "ProjectDashboard.fxml";
    private static final String CARD_STYLE = "card-border";
    private final Logger logger = LogsCenter.getLogger(ProjectDashboardView.class);

    @FXML
    private ListView<Task> taskListViewNotStarted;
    @FXML
    private ListView<Task> taskListViewDoing;
    @FXML
    private ListView<Task> taskListViewDone;
    @FXML
    private ListView<Task> taskListViewUpcomingDeadlines;
    @FXML
    private ListView<IndivMeetingCard> meetingListView;

    public ProjectDashboardView(ObservableList<Task> taskListNotStarted, ObservableList<Task> taskListDoing,
                                ObservableList<Task> taskListDone, ObservableList<Task> taskListDeadline,
                                ObservableList<Meeting> meetingList) {
        super(FXML);

        taskListViewNotStarted.setItems(taskListNotStarted);
        taskListViewDoing.setItems(taskListDoing);
        taskListViewDone.setItems(taskListDone);
        taskListViewUpcomingDeadlines.setItems(taskListDeadline);

        ObservableList<IndivMeetingCard> meetingCards = FXCollections.observableArrayList();
        for (int i = 0; i < meetingList.size(); i++) {
            Meeting meeting = meetingList.get(i);
            int indexFromOne = i + 1;
            IndivMeetingCard meetingCard = new IndivMeetingCard(indexFromOne, meeting);
            meetingCards.add(meetingCard);
        }
        meetingListView.setItems(meetingCards);

        taskListViewNotStarted.setCellFactory(listView -> new TaskListViewCell());
        taskListViewDoing.setCellFactory(listView -> new TaskListViewCell());
        taskListViewDone.setCellFactory(listView -> new TaskListViewCell());
        taskListViewUpcomingDeadlines.setCellFactory(listView -> new TaskListViewCell());
        meetingListView.setCellFactory(listView -> new MeetingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                TaskCard cardToRender = new TaskCard(task, getIndex() + 1);
                cardToRender.getRoot().getStyleClass().clear();
                cardToRender.getRoot().getStyleClass().add(CARD_STYLE);
                setGraphic(cardToRender.getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class MeetingListViewCell extends ListCell<IndivMeetingCard> {
        @Override
        protected void updateItem(IndivMeetingCard meetingCard, boolean empty) {
            super.updateItem(meetingCard, empty);

            if (empty || meetingCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                meetingCard.getRoot().getStyleClass().clear();
                meetingCard.getRoot().getStyleClass().add(CARD_STYLE);
                setGraphic(meetingCard.getRoot());
            }
        }
    }

}
