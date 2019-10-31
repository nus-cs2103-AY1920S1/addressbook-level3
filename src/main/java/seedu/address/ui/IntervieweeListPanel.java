package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Interviewee;

/**
 * Panel containing the list of persons.
 */
public class IntervieweeListPanel extends UiPart<Region> {
    private static final String FXML = "IntervieweeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IntervieweeListPanel.class);

    @FXML
    private ListView<Interviewee> intervieweeListView;

    public IntervieweeListPanel(ObservableList<Interviewee> interviewees) {
        super(FXML);
        intervieweeListView.setItems(interviewees);
        intervieweeListView.setCellFactory(listView -> new IntervieweeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Interviewee} using a {@code IntervieweeCard}.
     */
    class IntervieweeListViewCell extends ListCell<Interviewee> {
        @Override
        protected void updateItem(Interviewee interviewee, boolean empty) {
            super.updateItem(interviewee, empty);

            if (empty || interviewee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IntervieweeCard(interviewee, getIndex() + 1).getRoot());
            }
        }
    }

}
