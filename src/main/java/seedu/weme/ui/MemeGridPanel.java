package seedu.weme.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;


/**
 * Panel containing the list of memes.
 */
public class MemeGridPanel extends UiPart<Region> {
    private static final String FXML = "MemeGridPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemeGridPanel.class);
    private final ObservableMap<String, SimpleIntegerProperty> likeData;
    private final ObservableMap<String, SimpleIntegerProperty> dislikeData;
    private final Map<ImagePath, MemeCard> memeCardMap = new HashMap<>();

    @FXML
    private GridView<Meme> memeGridView;

    public MemeGridPanel(ObservableList<Meme> memeList,
                         ObservableMap<String, SimpleIntegerProperty> likeData,
                         ObservableMap<String, SimpleIntegerProperty> dislikeData) {
        super(FXML);
        memeGridView.setItems(memeList);
        memeGridView.setCellFactory(listView -> new MemeGridViewCell());
        this.likeData = likeData;
        this.dislikeData = dislikeData;
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
                // Cache the meme card to improve performance
                ImagePath imagePath = meme.getImagePath();
                MemeCard card = memeCardMap.get(imagePath);
                int newIndex = getIndex() + 1;
                SimpleIntegerProperty likes = likeData.get(imagePath.toString());
                SimpleIntegerProperty dislikes = dislikeData.get(imagePath.toString());
                if (card == null) {
                    card = new MemeCard(meme, newIndex, likes, dislikes);
                    memeCardMap.put(imagePath, card);
                } else {
                    card.update(meme, newIndex, likes, dislikes);
                }
                setGraphic(card.getRoot());
            }
        }
    }

}
