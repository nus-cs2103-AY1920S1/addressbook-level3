package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Meme;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Meme> personListView;

    public PersonListPanel(ObservableList<Meme> memeList) {
        super(FXML);
        personListView.setItems(memeList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Meme} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Meme> {
        @Override
        protected void updateItem(Meme meme, boolean empty) {
            super.updateItem(meme, empty);

            if (empty || meme == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(meme, getIndex() + 1).getRoot());
            }
        }
    }

}
