package seedu.weme.ui;

import java.util.logging.Logger;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;


/**
 * Panel containing the list of memes.
 */
public class ImportGridPanel extends UiPart<Region> {
    private static final String FXML = "ImportGridPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemeGridPanel.class);

    @FXML
    private GridView<Meme> memeGridView;

    public ImportGridPanel(ObservableList<Meme> memeList) {
        super(FXML);
        memeGridView.setItems(memeList);
        memeGridView.setCellFactory(listView -> new MemeGridViewCell());
    }

    /**
     * Custom {@code GridCell} that displays the graphics of a {@code Meme} using a {@code MemeCard}.
     */
    class MemeGridViewCell extends GridCell<Meme> {
        @Override
        protected void updateItem(Meme meme, boolean empty) {
            super.updateItem(meme, empty);
            if (empty || meme == null) {
                setGraphic(null);
                setText(null);
            } else {
                String filePath = meme.getImagePath().toString();
                setGraphic(new ImportMemeCard(meme, getIndex() + 1).getRoot());
            }
        }
    }

}
