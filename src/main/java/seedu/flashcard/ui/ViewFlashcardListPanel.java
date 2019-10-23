package seedu.flashcard.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.model.flashcard.Flashcard;

import java.util.logging.Logger;

public class ViewFlashcardListPanel extends UiPart<Region> {

    private static final String FXML = "ViewFlashcardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewFlashcardListPanel.class);

    @FXML
    private ListView<Flashcard> viewFlashcardListView;

    public ViewFlashcardListPanel(ObservableList<Flashcard> flashcardList, boolean isQuiz) {
        super(FXML);
        viewFlashcardListView.setItems(flashcardList);
        if (isQuiz) {
            viewFlashcardListView.setCellFactory(listView -> new ViewMediumFlashcardListViewCell());
        } else {
            viewFlashcardListView.setCellFactory(listView -> new ViewFullFlashcardListViewCell());
        }
    }

    class ViewMediumFlashcardListViewCell extends ListCell<Flashcard> {
        @Override
        protected void updateItem(Flashcard flashcard, boolean empty) {
            super.updateItem(flashcard, empty);
            if (empty || flashcard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MediumFlashcardCard(flashcard, getIndex() + 1).getRoot());
            }
        }
    }

    class ViewFullFlashcardListViewCell extends ListCell<Flashcard> {
        @Override
        protected void updateItem(Flashcard flashcard, boolean empty) {
            super.updateItem(flashcard, empty);
            if (empty || flashcard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FullFlashcardCard(flashcard, getIndex() + 1).getRoot());
            }
        }
    }
}
