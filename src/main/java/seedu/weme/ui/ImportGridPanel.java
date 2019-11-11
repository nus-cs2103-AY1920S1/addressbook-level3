package seedu.weme.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;


/**
 * Panel containing the list of memes in the import tab.
 */
public class ImportGridPanel extends UiPart<Region> {
    private static final String FXML = "ImportGridPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ImportGridPanel.class);
    private final Map<ImagePath, ImportMemeCard> importMemeCardMap = new HashMap<>();

    @FXML
    private GridView<Meme> importGridView;

    public ImportGridPanel(ObservableList<Meme> memeList) {
        super(FXML);
        importGridView.setItems(memeList);
        importGridView.setCellFactory(listView -> new MemeGridViewCell());
    }

    /**
     * Custom {@code GridCell} that displays the graphics of a {@code Meme} using a {@code ImportMemeCard}.
     */
    class MemeGridViewCell extends GridCell<Meme> {
        @Override
        protected void updateItem(Meme meme, boolean empty) {
            super.updateItem(meme, empty);
            if (empty || meme == null) {
                setGraphic(null);
                setText(null);
            } else {
                ImagePath imagePath = meme.getImagePath();
                ImportMemeCard card = importMemeCardMap.get(imagePath);
                int newIndex = getIndex() + 1;
                if (card == null) {
                    card = new ImportMemeCard(meme, newIndex);
                    importMemeCardMap.put(imagePath, card);
                } else {
                    card.update(meme, newIndex);
                }
                setGraphic(card.getRoot());
            }
        }
    }

}
