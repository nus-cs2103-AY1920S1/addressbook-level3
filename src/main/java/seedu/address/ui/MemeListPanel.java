package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.meme.Meme;

/**
 * Panel containing the list of memes.
 */
public class MemeListPanel extends UiPart<Region> {
    private static final String FXML = "MemeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemeListPanel.class);

    @FXML
    private ListView<Meme> memeListView;

    public MemeListPanel(ObservableList<Meme> memeList) {
        super(FXML);
        memeListView.setItems(memeList);
        memeListView.setCellFactory(listView -> new MemeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Meme} using a {@code MemeCard}.
     */
    class MemeListViewCell extends ListCell<Meme> {
        @Override
        protected void updateItem(Meme meme, boolean empty) {
            super.updateItem(meme, empty);

            if (empty || meme == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MemeCard(meme, getIndex() + 1).getRoot());
            }
        }
    }

}
