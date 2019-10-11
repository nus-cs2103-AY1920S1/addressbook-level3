package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.flashcard.FlashCard;

/**
 * Panel containing the list of flashcards.
 */
public class FlashCardListPanel extends UiPart<Region> {
    private static final String FXML = "FlashCardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FlashCardListPanel.class);

    @FXML
    private ListView<FlashCard> flashCardListView;

    public FlashCardListPanel(ObservableList<FlashCard> flashCardList) {
        super(FXML);
        flashCardListView.setItems(flashCardList);
        flashCardListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code FlashCard} using a {@code FlashCardPanel}.
     */
    class PersonListViewCell extends ListCell<FlashCard> {
        @Override
        protected void updateItem(FlashCard flashCard, boolean empty) {
            super.updateItem(flashCard, empty);

            if (empty || flashCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FlashCardPanel(flashCard, getIndex() + 1).getRoot());
            }
        }
    }

}
