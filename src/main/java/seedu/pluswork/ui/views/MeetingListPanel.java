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
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.ui.UiPart;

/**
 * Panel containing the list of tasks.
 * Called by {@code UserViewUpdate} when user executes {@code LisTaskCommand}.
 */
public class MeetingListPanel extends UiPart<Region> {
    private static final String FXML = "MeetingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingListPanel.class);

    @FXML
    private ListView<IndivMeetingCard> meetingListView;

    public MeetingListPanel(MeetingQuery meetingQuery) {
        super(FXML);
        ObservableList<IndivMeetingCard> meetingCards = FXCollections.observableArrayList();
        ObservableList<Meeting> meetingList = meetingQuery.getMeetingList();

        for (int i = 0; i < meetingList.size(); i++) {
            Meeting meeting = meetingList.get(i);
            int indexFromOne = i + 1;
            IndivMeetingCard meetingCard = new IndivMeetingCard(indexFromOne, meeting);
            meetingCards.add(meetingCard);
        }

        meetingListView.setItems(meetingCards);
        meetingListView.setCellFactory(listView -> new MeetingListViewCell());
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
                setGraphic(meetingCard.getRoot());
            }
        }
    }

}

