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
 * Panel consisting a list of interviewees
 */
public class IntervieweeViewPanel extends UiPart<Region> {
    private static final String FXML = "IntervieweeViewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Interviewee> intervieweeListView;

    public IntervieweeViewPanel(ObservableList<Interviewee> intervieweeList) {
        super(FXML);
        intervieweeListView.setItems(intervieweeList);
        intervieweeListView.setCellFactory(listView -> new IntervieweeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
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
