package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Interviewer;

/**
 * Panel containing the list of persons.
 */
public class InterviewerListPanel extends UiPart<Region> {
    private static final String FXML = "InterviewerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InterviewerListPanel.class);

    @FXML
    private ListView<Interviewer> interviewerListView;

    public InterviewerListPanel(ObservableList<Interviewer> interviewers) {
        super(FXML);
        interviewerListView.setItems(interviewers);
        interviewerListView.setCellFactory(listView -> new InterviewerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Interviewer} using a {@code InterviewerCard}.
     */
    class InterviewerListViewCell extends ListCell<Interviewer> {
        @Override
        protected void updateItem(Interviewer interviewer, boolean empty) {
            super.updateItem(interviewer, empty);

            if (empty || interviewer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InterviewerCard(interviewer, getIndex() + 1).getRoot());
            }
        }
    }
}
