package seedu.address.ui.entitylistpanel;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import seedu.address.model.entity.Mentor;
import seedu.address.ui.EntityCard;

/**
 * Panel containing the list of persons.
 */
public class MentorListPanel extends EntityListPanel {
    private static final String FXML = "MentorListPanel.fxml";

    @FXML
    private ListView<Mentor> mentorListView;

    public MentorListPanel(ObservableList<Mentor> mentorList) {
        super(FXML);
        mentorListView.setItems(mentorList);
        mentorListView.setCellFactory(listView -> new MentorListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class MentorListViewCell extends ListCell<Mentor> {
        @Override
        protected void updateItem(Mentor mentor, boolean isEmpty) {
            super.updateItem(mentor, isEmpty);

            if (isEmpty || mentor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntityCard(mentor, getIndex() + 1).getRoot());
            }
        }
    }
}

