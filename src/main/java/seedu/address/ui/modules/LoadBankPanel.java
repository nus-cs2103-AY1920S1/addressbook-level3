package seedu.address.ui.modules;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.wordbank.WordBank;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of word banks.
 */
public class LoadBankPanel extends UiPart<Region> {
    private static final String FXML = "LoadBankPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LoadBankPanel.class);

    @FXML
    private ListView<WordBank> loadBankView;

    public LoadBankPanel(ObservableList<WordBank> wordBankList) {
        super(FXML);
        loadBankView.setItems(wordBankList);
        loadBankView.setCellFactory(listView -> new LoadBankViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code CardCard}.
     */
    class LoadBankViewCell extends ListCell<WordBank> {
        @Override
        protected void updateItem(WordBank wordBank, boolean empty) {
            super.updateItem(wordBank, empty);

            if (empty || wordBank == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WordBankCard(wordBank, getIndex() + 1).getRoot());
            }
        }
    }

}
