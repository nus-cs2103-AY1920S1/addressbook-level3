package seedu.weme.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.weme.model.template.MemeText;

/**
 * Panel showing all the currently added meme texts.
 */
public class MemeTextListPanel extends UiPart<Region> {
    private static final String FXML = "MemeTextListPanel.fxml";

    @FXML
    private ListView<MemeText> memeTextListView;

    public MemeTextListPanel(ObservableList<MemeText> memeTexts) {
        super(FXML);
        memeTextListView.setItems(memeTexts);
        memeTextListView.setCellFactory(listView -> new MemeTextListViewCell());
        // Auto-scroll to last item
        memeTextListView.getItems().addListener((ListChangeListener<MemeText>) (c -> {
            c.next();
            final int size = memeTextListView.getItems().size();
            if (size > 0) {
                memeTextListView.scrollTo(size - 1);
            }
        }));
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MemeText} using a {@code MemeTextCard}.
     */
    class MemeTextListViewCell extends ListCell<MemeText> {
        @Override
        protected void updateItem(MemeText memeText, boolean empty) {
            super.updateItem(memeText, empty);
            if (empty || memeText == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MemeTextCard(memeText, getIndex() + 1).getRoot());
            }
        }
    }

}
